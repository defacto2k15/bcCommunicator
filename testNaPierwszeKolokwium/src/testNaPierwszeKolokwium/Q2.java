package testNaPierwszeKolokwium;

import java.awt.Button;
import java.awt.Frame;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.MalformedURLException;
import java.net.URL;




public class Q2 extends Frame {
private Q2 (){
	setSize(300, 300);
	Button b = new Button("Apply");
	add(b); // a.
}
public static void main(String args[]) {
Q2 that = new Q2(); // b.
that.setVisible(true);
}
} 
