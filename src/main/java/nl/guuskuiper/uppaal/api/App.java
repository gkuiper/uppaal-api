package nl.guuskuiper.uppaal.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import nl.guuskuiper.uppaal.api.MyQueryFeedback;

import com.uppaal.engine.*;
import com.uppaal.model.core2.Document;
import com.uppaal.model.core2.PrototypeDocument;
import com.uppaal.model.core2.QueryListModel;
import com.uppaal.model.core2.QueryProperty;
import com.uppaal.model.system.UppaalSystem;

/**
 * 
 * @author guusk
 * Uppaal API documentation on: http://people.cs.aau.dk/~marius/modeldoc/
 */
public class App {
	
	public static <T> T last(T[] array) {
	    return array[array.length - 1];
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws EngineException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws EngineException, IOException, InterruptedException {
		Engine engine = new Engine(EngineStub.SERVER, EngineStub.DEFAULT_PORT, EngineStub.DEFAULT_HOST, "");
		engine.connect();
		
		System.out.println(engine.getVersion());
		//System.out.println(engine.getOptionsInfo());
		
		//File file = new File("model_task.xml");
		File file = new File("model.xml");
		URL xmlUrl = file.toURI().toURL();
		Document doc = new PrototypeDocument().load(xmlUrl);
		
		ArrayList<Problem> problems = new ArrayList<Problem>();
		UppaalSystem system = engine.getSystem(doc, problems);
		
		QueryListModel qm = doc.getQueryListModel();
		System.out.println("Queries: " + qm.getSize());
		
		for(Enumeration<QueryProperty> e = qm.elements(); e.hasMoreElements();)
			System.out.println(e.nextElement().getFormula());
		
		String q = qm.firstElement().getFormula();
		MyQueryFeedback feedback = new MyQueryFeedback();
		
		QueryVerificationResult result = engine.query(system, "", q, feedback);
		System.out.println(result.result);
		System.out.println(feedback.getFeedback());
		System.out.println("Finished");
		
		String queryFeedback = feedback.getFeedback();
		String lines[] = queryFeedback.split("\\r?\\n");
		String qs = last(q.split(" "));
		for (String line : lines) {
			if(line.startsWith(qs)) {
				int qr = Integer.parseInt(last(line.split(" ")));
				System.out.println(q + " resulted in <= " + qr);
			}
		}
	}

}
