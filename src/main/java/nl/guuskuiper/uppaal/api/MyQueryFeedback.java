package nl.guuskuiper.uppaal.api;

import java.util.ArrayList;

import com.uppaal.engine.QueryFeedback;
import com.uppaal.engine.QueryVerificationResult;
import com.uppaal.model.system.symbolic.SymbolicTransition;

public class MyQueryFeedback implements QueryFeedback {
	
	public String feedback;

	public String getFeedback() {
		return this.feedback;
	}
	
	// Implement the Interface functions of QueryFeedback
	public void setLength(int length) {
		System.out.println("Trace length: " + length);
	}

	public void setCurrent(int pos) {
		System.out.println("Received " + pos + " transitions");
	}

	public void appendText(String s) {
		System.out.println("Append text: " + s);
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
		System.out.println("Feedback text: " + feedback);
	}

	public void setProgress(int load, long vm, long rss, long cached,
			long avail, long swap, long swapfree, long user, long sys,
			long timestamp) {
		System.out.println("Progress load: " + load);
	}

	public void setProgressAvail(boolean availability) {
		System.out.println("Progress available: " + availability);
		
	}

	public void setResultText(String s) {
		System.out.println("Result: " + s);

	}

	public void setSystemInfo(long vmsize, long physsize, long swapsize) {
		System.out.println("System vmsize/physize/swapsize: " + vmsize + "/" + physsize + "/" + swapsize + " kB");
	}

	public void setTrace(char result, String feedback,
			ArrayList<SymbolicTransition> trace, int cycle,
			QueryVerificationResult arg4) {
		this.feedback = feedback;
		System.out.println("Received a trace");
	}
};
