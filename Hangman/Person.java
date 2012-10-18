import acm.graphics.*;
import java.awt.*;

public class Person extends GRect{
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	private static final int MAX_PARTS = 8;
	
	private int numVisibleParts;
	
	private int offsetX, offsetY;
	
	private int move, move2;
	
	private point headPoint, leftUpperArm, leftLowerArm, rightUpperArm, rightLowerArm, armBodyPoint, 
				leftUpperLeg, leftLowerLeg, leftFoot, rightUpperLeg, rightLowerLeg, rightFoot, bodyStart, bodyEnd;
	
	public Person(int x, int y) {
		super(0,0);
		offsetX = x;
		offsetY = y;
		numVisibleParts = -1;
		loadPoints();
		move = 0;
		move2 = 0;
	}
	
	public void loadPoints() {
		headPoint     = new point(offsetX,offsetY+HEAD_RADIUS);
		leftUpperArm  = new point(offsetX-UPPER_ARM_LENGTH,offsetY+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD);
		leftLowerArm  = new point(offsetX-UPPER_ARM_LENGTH,offsetY+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD+LOWER_ARM_LENGTH);
		rightUpperArm = new point(offsetX+UPPER_ARM_LENGTH,offsetY+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD);
		rightLowerArm = new point(offsetX+UPPER_ARM_LENGTH,offsetY+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD+LOWER_ARM_LENGTH);
		armBodyPoint  = new point(offsetX,offsetY+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD);
		leftUpperLeg  = new point(offsetX-HIP_WIDTH,offsetY+HEAD_RADIUS*2+BODY_LENGTH);
		leftLowerLeg  = new point(offsetX-HIP_WIDTH,offsetY+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH);
		rightUpperLeg = new point(offsetX+HIP_WIDTH,offsetY+HEAD_RADIUS*2+BODY_LENGTH);
		rightLowerLeg = new point(offsetX+HIP_WIDTH,offsetY+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH);
		leftFoot      = new point(offsetX-HIP_WIDTH-FOOT_LENGTH,offsetY+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH);
		rightFoot     = new point(offsetX+HIP_WIDTH+FOOT_LENGTH,offsetY+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH);
		bodyStart     = new point(offsetX,offsetY+HEAD_RADIUS*2);
		bodyEnd       = new point(offsetX,offsetY+HEAD_RADIUS*2+BODY_LENGTH);
	}
	
	public void addLimb() {
		numVisibleParts++;
	}
	
	public void paint(Graphics g) {
		drawPart(numVisibleParts,g);
	}
	
	public void drawPart(int part ,Graphics g) {
		switch(part) {
		case 8:
		case 7:
			g.drawLine(rightFoot.getX(),rightFoot.getY(),rightLowerLeg.getX(),rightLowerLeg.getY());
		case 6:
			g.drawLine(leftFoot.getX(),leftFoot.getY(),leftLowerLeg.getX(),leftLowerLeg.getY());
		case 5:
			g.drawLine(rightUpperLeg.getX(),rightUpperLeg.getY(),bodyEnd.getX(),bodyEnd.getY());
			g.drawLine(rightLowerLeg.getX(),rightLowerLeg.getY(),rightUpperLeg.getX(),rightUpperLeg.getY());
		case 4:
			g.drawLine(leftUpperLeg.getX(),leftUpperLeg.getY(),bodyEnd.getX(),bodyEnd.getY());
			g.drawLine(leftLowerLeg.getX(),leftLowerLeg.getY(),leftUpperLeg.getX(),leftUpperLeg.getY());
		case 3:
			g.drawLine(rightUpperArm.getX(),rightUpperArm.getY(),armBodyPoint.getX(),armBodyPoint.getY());
			g.drawLine(rightLowerArm.getX(),rightLowerArm.getY(),rightUpperArm.getX(),rightUpperArm.getY());
		case 2:
			g.drawLine(leftUpperArm.getX(),leftUpperArm.getY(),armBodyPoint.getX(),armBodyPoint.getY());
			g.drawLine(leftLowerArm.getX(),leftLowerArm.getY(),leftUpperArm.getX(),leftUpperArm.getY());
		case 1:
			g.drawLine(bodyStart.getX(),bodyStart.getY(),bodyEnd.getX(),bodyEnd.getY());
		case 0:
			g.setColor(Color.white);
			g.fillOval(headPoint.getX()-HEAD_RADIUS, headPoint.getY()-HEAD_RADIUS, HEAD_RADIUS*2, HEAD_RADIUS*2);
			g.setColor(Color.black);
			g.drawOval(headPoint.getX()-HEAD_RADIUS, headPoint.getY()-HEAD_RADIUS, HEAD_RADIUS*2, HEAD_RADIUS*2);
			break;
		default: break;
		}
		if(numVisibleParts == 8) {
			g.drawString("X", headPoint.getX()-10, headPoint.getY()-5);
			g.drawString("X", headPoint.getX()+10, headPoint.getY()+10);
		}
	}
	
	public void moveLimb() {
		leftUpperArm.translate(armBodyPoint,UPPER_ARM_LENGTH,move,-1);
		rightUpperArm.translate(armBodyPoint,UPPER_ARM_LENGTH,move,1);
		leftLowerArm.translate(leftUpperArm, LOWER_ARM_LENGTH, 90, -1);
		rightLowerArm.translate(rightUpperArm, LOWER_ARM_LENGTH, 90, 1);
		leftUpperLeg.translate(bodyEnd, HIP_WIDTH, move, -1);
		rightUpperLeg.translate(bodyEnd, HIP_WIDTH, move, 1);
		leftLowerLeg.translate(leftUpperLeg, LEG_LENGTH, 90, -1);
		rightLowerLeg.translate(rightUpperLeg, LEG_LENGTH, 90, 1);
		leftFoot.translate(leftLowerLeg, FOOT_LENGTH, move, -1);
		rightFoot.translate(rightLowerLeg, FOOT_LENGTH, move, 1);
		
		move+=2;
	}
	public void moveHead() {
		headPoint.translate(bodyStart,HEAD_RADIUS,move2-90,1);
		move2+=2;
	}
}

class point {
	private int defaultX;
	private int defaultY;
	
	double x, y;
	
	public point(int x, int y) {
		defaultX = x;
		defaultY = y;
		this.x = x;
		this.y = y;
	}
	
	public void translate(point pt, int length, double angle, int sign) {
		x = pt.x+sign*length*Math.cos(Math.toRadians(angle));
		y = pt.y+length*Math.sin(Math.toRadians(angle));
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
}

