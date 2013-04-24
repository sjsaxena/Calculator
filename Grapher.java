/*
 * TEAM 8
 * Michael Glander msglande
 * Travis Tippens tctippen
 * Shreye Saxena sjsaxena
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.math.BigDecimal;
import java.util.*;

import javax.swing.*;



public class Grapher extends JPanel implements MouseListener
	{
	String expression;
	ArrayList<ArrayList<Double>> givenPoints;
	double lowX, highX, lowY, highY;
	int ticksX, ticksY;
	int x0, y0 = 0;
	Calculator calculator = new Calculator();
	//JWindow graphWindow = new JWindow();
	JFrame graphFrame = new JFrame();
	JPopupMenu popupMenu = new JPopupMenu();
	JMenuItem menuItem = new JMenuItem("              ");
	
	public Grapher(String expression, ArrayList<ArrayList<Double>> givenPoints, double lowX, double highX, int ticksX, double lowY, double highY, int ticksY, Calculator calculator)
		{	
		this.expression = expression;
		this.givenPoints = givenPoints;
		this.lowX = lowX;
		this.highX = highX;
		this.ticksX = ticksX;
		this.lowY = lowY;
		this.highY = highY;
		this.ticksY = ticksY;
		this.calculator = calculator;
		
		popupMenu.add(menuItem);
		System.out.println(expression +" "+ givenPoints +" "+ lowX +" "+ highX +" "+ ticksX +" "+ lowY +" "+ highY +" "+ ticksY);
		
		
		graphFrame.add(this, "Center");
		graphFrame.setSize(800,600); // width, height
		graphFrame.setTitle(expression);
		graphFrame.setVisible(true);
		graphFrame.addMouseListener(this);
		graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

/*	public static void main(String[] args)
		{
		ArrayList<Double> xval = new ArrayList<Double>(Arrays.asList(-10.0, -9.0,-8.0,-7.0,-6.0,-5.0,-4.0,-3.0,-2.0,-1.0,0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0)); 
		ArrayList<Double> yval = new ArrayList<Double>(Arrays.asList(-100.0, -81.0, -64.0, -49.0, -36.0, -25.0, -16.0, -9.0, -4.0, - 1.0,0.0,1.0,4.0,9.0,16.0,25.0,36.0,49.0,64.0,81.0,100.0)); 
		ArrayList<ArrayList<Double>> lolcatz = new ArrayList<ArrayList<Double>>(Arrays.asList(xval,yval));
		
		new Grapher("trolololol", lolcatz, -10, 10, 4, -10, 10, 4, null);
		}
*/
	public void paint(Graphics g)
		{// take double ArrayList and make it an integer Array LIst
		ArrayList<Integer> xVal = new ArrayList<Integer>();
		for(Double x : givenPoints.get(0))
			xVal.add((int) Math.round(x));
		ArrayList<Integer> yVal = new ArrayList<Integer>();
		for(Double y : givenPoints.get(1))
			yVal.add((int) Math.round(y));
		ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>(Arrays.asList(xVal, yVal));
		
		
		int i;
		super.paint(g);
		Dimension screen = graphFrame.getSize();
		int screenHeight = screen.height - 22;
		int screenWidth = screen.width - 50; 
		int yBuffer = 50;
		int xBuffer = 50;
		
		//System.out.println("Screen: " + screenWidth + " " + screenHeight);
		
		int plotHeight = (screenHeight) - yBuffer;
		int plotWidth = screenWidth - xBuffer+1;
		
		//System.out.println("Plot: " + plotWidth + " " + plotHeight);
		
		int xRange = ( Collections.max(points.get(0)) - Collections.min(points.get(0)) );
		//System.out.println(xRange);
		
		int yRange = ( Collections.max(points.get(1)) - Collections.min(points.get(1)) );
		
		int pixelX = plotWidth/xRange;
		int pixelY = plotHeight/yRange;
		
		//System.out.println("Pixel: " + pixelX + " " + pixelY);

		int xIncrement = ((xBuffer+1+pixelX*( Collections.max(points.get(0)) - Collections.min(points.get(0)) )) - (xBuffer+1))/(ticksX);
		int yIncrement = ( (screenHeight - yBuffer) - (5+screenHeight-yBuffer-pixelY*( Collections.max(points.get(1)) - Collections.min(points.get(1)) )))/(ticksY);
		
		// axis lines
		g.drawLine(xBuffer, screenHeight-yBuffer, 5+(xBuffer+1+pixelX*( Collections.max(points.get(0)) - Collections.min(points.get(0)) )), screenHeight-yBuffer); //horizontal
		g.drawLine(xBuffer, screenHeight-yBuffer+1, 5+(xBuffer+1+pixelX*( Collections.max(points.get(0)) - Collections.min(points.get(0)) )), screenHeight-yBuffer+1);
		g.drawLine(xBuffer, 5+screenHeight-yBuffer-pixelY*( Collections.max(points.get(1)) - Collections.min(points.get(1)) ) , xBuffer, screenHeight-yBuffer); //vertical
		g.drawLine(xBuffer+1, 5+screenHeight-yBuffer-pixelY*( Collections.max(points.get(1)) - Collections.min(points.get(1)) ) , xBuffer+1, screenHeight-yBuffer);
		
		// axis labels
		g.drawString("X", (screenWidth/2), (screenHeight-yBuffer/4));
		g.drawString("Y", 0, screenHeight/2);
		
		// ticks
		for(i=1; i<= ticksX; i++)
			g.drawString("|", 5+xBuffer+xIncrement*i, screenHeight-yBuffer);
		for(i=1; i<= ticksY; i++)
			g.drawString("-", xBuffer, screenHeight-yBuffer-yIncrement*i);
		
		// tick labels
		for(i=1; i<= ticksX; i++){
			g.drawString((new BigDecimal((Collections.min(givenPoints.get(0)) + ((double) xRange/(double) ticksX)*i)).setScale(2, BigDecimal.ROUND_HALF_UP).toString()), xBuffer+xIncrement*i-10, screenHeight-yBuffer+yBuffer/4);
			//g.drawString(Integer.toString((int) Math.round((Collections.min(points.get(0)) + ((double) xRange/(double) ticksX)*i))), xBuffer+xIncrement*i-10, screenHeight-yBuffer+yBuffer/4);
	//		System.out.println((Collections.min(points.get(0)) + (xRange/ticksX)*i));
	//		System.out.println((xRange/ticksX)*i);
	//		System.out.println(xRange + " "  + ticksX + " " + (xRange/ticksX));
		}
		for(i=1; i<= ticksY; i++)
			g.drawString(Integer.toString((int) Math.round((Collections.min(points.get(1)) + ((double) yRange/(double) ticksY)*i))), 3*xBuffer/4-25, screenHeight-yBuffer-yIncrement*i);
			//g.drawString(Integer.toString(Collections.min(points.get(1)) + (yRange/ticksY)*i), 3*xBuffer/4-25, screenHeight-yBuffer-yIncrement*i);
		
		// change color
		g.setColor(Color.BLUE);
		
		// plot points
		for(i=0; i<points.get(0).size(); i++)
			g.drawString("O", xBuffer+1+pixelX*( points.get(0).get(i) - Collections.min(points.get(0)) ), 5+screenHeight-yBuffer-pixelY*( points.get(1).get(i) - Collections.min(points.get(1)) ) );
		
		// plot lines between points
		for(i=0; i<points.get(0).size()-1; i++)
			{
			g.drawLine(xBuffer+1+pixelX*( points.get(0).get(i) - Collections.min(points.get(0)) ), screenHeight-yBuffer-pixelY*( points.get(1).get(i) - Collections.min(points.get(1)) ), xBuffer+1+pixelX*( points.get(0).get(i+1) - Collections.min(points.get(0)) ), screenHeight-yBuffer-pixelY*( points.get(1).get(i+1) - Collections.min(points.get(1)) ));
			}
		
		//change color
		g.setColor(Color.RED);
		
		 //create a "y=0" line if necessary
		if((Collections.min(points.get(1)) < 0) && (Collections.max(points.get(1)) > 0 ))
			{
			g.drawLine(xBuffer, screenHeight-yBuffer-pixelY*( y0 - Collections.min(points.get(1)) ) , screenWidth, screenHeight-yBuffer-pixelY*( y0 - Collections.min(points.get(1)) ));
			}
		
		//create a "x=0" line if necessary
		if((Collections.min(points.get(0)) < 0) && (Collections.max(points.get(0)) > 0 ))
			{
			g.drawLine(5+xBuffer+1+pixelX*(x0 - Collections.min(points.get(0)) ) , 0, 5+xBuffer+1+pixelX*(x0 - Collections.min(points.get(0))) , screenHeight-yBuffer);
			}
		
		}


	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}


	public void mousePressed(MouseEvent me)
		{
			//Magic stuff that Shreye wrote
			ArrayList<Integer> xVal = new ArrayList<Integer>();
			for(Double x : givenPoints.get(0))
				xVal.add((int) Math.round(x));
			ArrayList<Integer> yVal = new ArrayList<Integer>();
			for(Double y : givenPoints.get(1))
				yVal.add((int) Math.round(y));
			ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>(Arrays.asList(xVal, yVal));
			
			double xBuffer = 50;
			double ratio = ((double) me.getX() - (xBuffer + 1))/((double) (graphFrame.getWidth() - (xBuffer+1)));
			int xRange = ( Collections.max(points.get(0)) - Collections.min(points.get(0)) );
			double valueX = lowX + (ratio * xRange) ;
			String replacedString = expression.replace("x", Double.toString(valueX));
			String text = "For x = " + new BigDecimal(valueX).setScale(2, BigDecimal.ROUND_HALF_UP) + ", y = " + calculator.calculatorFunction(replacedString);
			menuItem.setText(text);
			
			popupMenu.show(graphFrame, me.getX(), me.getY());
		}


	public void mouseReleased(MouseEvent arg0)
		{
		popupMenu.setVisible(false);
		}
	
	}
