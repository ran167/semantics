package com.abb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.abb.nlp.DisplayData;
import com.abb.nlp.PartsOfSpeech;
import com.abb.nlp.PosDisplayData;
import com.abb.nlp.SenTokenizer;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.StopWordRemover;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadReadServlet")
public class UploadReadServlet extends HttpServlet {

	private static ILexicalDatabase db = new NictWordNet();

	private static final String STOPFILENAME = "C:\\Users\\prath\\Desktop\\inhouse project\\project 4\\ABBMachineLearning\\WebContent\\stoplist.txt";

	public UploadReadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader br = null;
		FileReader fr = null;
		List<String> stopWordsList = null;
		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(STOPFILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			stopWordsList = new ArrayList<String>();

			while ((sCurrentLine = br.readLine()) != null) {
				stopWordsList.add(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		response.setContentType("text/html;charset=UTF-8");
		String fileName = request.getParameter("file");
		String fileName2 = request.getParameter("file2");
		InputStream filecontent = null;
		String sentence = "";
		ArrayList<String> senlist = new ArrayList<String>();
		final PrintWriter writer = response.getWriter();
		System.out.println("&&&&&&&pTH:" + fileName);
		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				sentence = sentence + line;

			}
			/*
			 * reading lines of 2nd file
			 */
			// FileReader reads text files in the default encoding.
			String sentence2 = "";
			FileReader fileReader2 = new FileReader(fileName2);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);

			while ((line = bufferedReader2.readLine()) != null) {
				sentence2 = sentence2 + line;

			}
			String sen[] = com.abb.mynlps.SentenceFinder.readSentence(sentence);
			String sen2[] = com.abb.mynlps.SentenceFinder.readSentence(sentence2);
			String op = request.getParameter("op");
			System.out.println("&&&&&&&&&&&&&&&&&&&&op" + op);
			if (op.equals("sentence")) {
				System.out.println("Number of sentences:" + sen.length);
				request.setAttribute("sentencelist", sen);
				request.setAttribute("sentencelist2", sen2);
				request.setAttribute("message", "Following are the sentences:");
			}

			else if (op.equals("pos")) {
				System.out.println("&&&&&&&&&&&&&POS ");
				ArrayList<PosDisplayData> sen1PosList = new ArrayList<PosDisplayData>();
				ArrayList<PosDisplayData> a2 = new ArrayList<PosDisplayData>();

				for (int i = 0; i < sen.length; ++i) {
					PosDisplayData o = PartsOfSpeech.pos(sen[i]);
					sen1PosList.add(o);
				}
				request.setAttribute("poslist", sen1PosList);

				for (int i = 0; i < sen2.length; ++i) {
					PosDisplayData o = PartsOfSpeech.pos(sen2[i]);
					a2.add(o);
				}

				request.setAttribute("poslist2", a2);
				request.setAttribute("message", "Parts Of Speech Tagging:");

				WS4JConfiguration.getInstance().setMFS(true);
				List<Double> semvals = new ArrayList<Double>();
				WuPalmer wuPalmer = new WuPalmer(db);
				for (PosDisplayData data : sen1PosList) {
					for (PosDisplayData data2 : a2) {
						double semVal = 0.0;
						String[] tokens = data.getTokens();
						String[] tags = data.getTags();
						String[] tokens2 = data2.getTokens();
						String[] tags2 = data2.getTags();
						for (int i = 0; i < tags.length; i++) {
							for (int j = 0; j < tags2.length; j++) {
								if (tags[i].equals(tags2[j])) {
									if (!tokens[i].equals(".")) {

										boolean stopFlag1 = stopWordsList.contains(tokens[i]);
										boolean stopFlag2 = stopWordsList.contains(tokens2[j]);
										if (!stopFlag1 && !stopFlag2) {
											System.out.println(
													"Comparing semalarity for " + tokens[i] + " " + tokens2[j]);
											double calcRelatednessOfWords = wuPalmer.calcRelatednessOfWords(tokens[i],
													tokens2[j]);
											if (calcRelatednessOfWords > 1.0) {
												calcRelatednessOfWords = 1.0;
											}
											semVal = semVal + calcRelatednessOfWords;
											System.out.println("Relatedness = " + calcRelatednessOfWords);
											System.out.println("********");
										}

									}

								}
							}
						}
						semvals.add(semVal);
					}
				}

				for (Double val : semvals) {
					System.out.println(val);
				}

			}

			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("input.jsp");
		rd.forward(request, response);
	}
}
