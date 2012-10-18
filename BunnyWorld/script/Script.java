package script;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.applet.AudioClip;
import java.awt.Toolkit;

import sun.audio.AudioStream;
import bGUI.BGame;
import bGUI.BGameCanvas;

import data.BDataModel;
import data.BPage;
import data.BPageModel;
import data.BShape;
import data.BShapeModel;

/**
 * 
 * @author Chidozie Nwobilor
 *	class that defines what a script is
 *	need an instance of this class to access the methods.
 */
public class Script {
	public static final String ON = "on";
	public static final String CLICK = "click";
	public static final String ENTER = "enter";
	public static final String DROP = "drop";
	
	public static final String GOTO = "goto";
	public static final String PLAY = "play";
	public static final String HIDE = "hide";
	public static final String SHOW = "show";
	public static final String BEEP = "beep";
	
	private static Set<String> triggers;
	private static Set<String> actions;
	private static BDataModel data;
 	public Script(BDataModel data){
		triggers = setUpTriggerSet();
		actions = setUpActionsSet();
		Script.data = data;
	}
	private static Set<String> setUpTriggerSet() {
		Set<String> temp = new HashSet<String>();
		temp.add(CLICK);
		temp.add(ENTER);
		temp.add(DROP);
		return temp;
	}
	private static Set<String> setUpActionsSet() {
		Set<String> temp = new HashSet<String>();
		temp.add(GOTO);
		temp.add(PLAY);
		temp.add(HIDE);
		temp.add(SHOW);
		temp.add(BEEP);
		return temp;
	}
	/**
	 * determines whether an incoming string is a valid script. Doesn't check to see
	 * if the names are valid though...
	 * @param s
	 * @return
	 */
	public static boolean isValid(String input){
		Scanner sc = new Scanner(input);
		if(!sc.next().toLowerCase().equals(ON)) return false;
		String trigger = sc.next().toLowerCase();
		if(!triggers.contains(trigger)) return false;
		if(trigger.equals(DROP)){
			String s = sc.next();
			if(!data.hasShape(s) && !triggers.contains(s.toLowerCase())) return false;//have to see if requiring the other shape to be in the datamodel is problematic
		}
		return Validity(input.toLowerCase(), sc);
	}
	/*
	 * (non-javadoc)
	 * determines the validity of the rest of the string
	 */
	private static boolean Validity(String s, Scanner sc) {
		while(sc.hasNext()){
			String action = sc.next();
			if(!actions.contains(action)) return false;
			else{
				if(!action.equals(BEEP)){
					if(!sc.hasNext()) return false;
					if(actions.contains(sc.next())) return false;
				}
			}
		}
		return true;
	}
	/**
	 * extracts the trigger in the given script. should only be used on scripts
	 * that have already been determined to be valid.
	 * @param script
	 */
	public static String extractTrigger(String script){
		Scanner sc = new Scanner(script);
		String s = "";
		if(sc.hasNext()){
			sc.next();
			if(sc.hasNext())s += sc.next();
			if(s.equals(DROP)){
				return s + " " + sc.next();
			}else{
				return s;
			}
		}
		return s;
	}
	/**
	 * extracts the shapeName in a dropScript
	 * @param script
	 * @return
	 */
	public static String extractShapeName(String script){
		Scanner sc = new Scanner(script);
		sc.next();
		sc.next();
		return sc.next();
	}
	public static void execute(String script, BGameCanvas canvas) {
		//System.out.println(script);
		ArrayList<String> actions = extractActions(script);
		ExecuteActions(actions, canvas);
	}
	private static void ExecuteActions(ArrayList<String> actions, BGameCanvas canvas) {
		int count = 0;
		while(count < actions.size()){
			String action = actions.get(count);
			if(action.equals(BEEP)){
				Toolkit.getDefaultToolkit().beep();
			}else{
				count++;
				String target = "";
				int i = count;
				String temp = "";
				while(i < actions.size()){
					temp = actions.get(i);
					if(Script.actions.contains(temp)){
						count = i - 1;
						break;
					}
					target += temp + " ";
					i++;
				}
				if(i >= actions.size()) count = i;
				//System.out.println(target);
				target = target.substring(0, target.length() - 1);
				
				if(action.equals(GOTO)){
					//System.out.println(GOTO + " " + target + ";");
					canvas.flipTo(new BPage(canvas.getData().getPage(target)));
				}else if(action.equals(PLAY)){
					//System.out.println(PLAY + " " + target);
					canvas.getData().playAudio(target);
				}else if(action.equals(HIDE)){
					BShapeModel shape = canvas.getData().getShape(target);
					if(shape != null) shape.setHidden(true);
				}else{
					BShapeModel shape = canvas.getData().getShape(target);
					if(shape != null) shape.setHidden(false);
				}
			}
			count++;
		}
		
	}
	private static ArrayList<String> extractActions(String script) {
		Scanner sc = new Scanner(script);
		sc.next();
		String trigger = sc.next();
		if(IsDrop(trigger)) sc.next();
		ArrayList<String> actions = new ArrayList<String>();
		while(sc.hasNext()){
			actions.add(sc.next());
		}
		return actions;
	}
	private static boolean IsDrop(String trigger) {
		return trigger.equals(DROP);
	}
	
	public static Map<String, String> parseScript(String script){
		System.out.println("---------------------------------------");
		Map<String, String> scriptBlock = new HashMap<String, String>();
		Scanner sc = new Scanner(script);
		String s = "";
		if(sc.hasNext()) s += sc.next() + " ";
		while(sc.hasNext()){
			String next = sc.next();
			if(next.equals(ON)){
				System.out.println(s);
				if(isValid(s))scriptBlock.put(extractTrigger(s),s.toLowerCase());
				s = "";
			}
			s += next + " ";
		}
		System.out.println(s);
		if(s != "" && isValid(s))scriptBlock.put(extractTrigger(s), s);
		return scriptBlock;
	}
	/**
	 * makes the inputted string script safe so that i don't have to deal with the problem of shape names with multiple
	 * words in them.
	 * @param name
	 * @return
	 */
	public static String makeScriptSafe(String name) {
		Scanner s = new Scanner(name);
		String result = "";
		while(s.hasNext()){
			result += s.next();
			if(s.hasNext()) result += "_";
		}
		return result;
	}
}
