/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scala.swing.examples.tutorials.misc

import scala.swing._

/*
 * Tutorial: How to Use the Focus System
 * http://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/misc/FocusConceptsDemoProject/src/misc/FocusConceptsDemo.java
 *
 * FocusConceptsDemo.scala requires no other files.
 * 
 * When running the demo, try the following:
 * 1. If necessary, click the window to give it the focus.
 * 2. Move the focus from component to component using the Tab key.
 * 3. You will notice that when the focus moves into the text area, it stays in the text area.
 * 4. Move the focus out of the text area using Control-Tab.
 * 5. Move the focus in the opposite direction using Shift-Tab.
 * 6. Move the focus out of the text area in the opposite direction using Control-Shift-Tab.
 */
class FocusConceptsDemo extends BorderPanel {
  val b1 = new Button("Button")
  val b2 = new Button("Button")
  val b3 = new Button("Button")
  val b4 = new Button("Button")
  
  val buttonPanel = new GridPanel(1, 1) {
    contents += b1
    contents += b2
    contents += b3
    contents += b4
  }
  
  val text1 = new TextArea("TextArea", 15, 40)
  val textAreaPanel = new BorderPanel() {
    layout(text1) = BorderPanel.Position.Center
  }
  
  val t1 = new TextField("TextField")
  val t2 = new TextField("TextField")
  val t3 = new TextField("TextField")
  val t4 = new TextField("TextField")
  val textFieldPanel = new GridPanel(1,1) {
    contents += t1
    contents += t2
    contents += t3
    contents += t4
  }
  
  layout(buttonPanel) = BorderPanel.Position.North
  layout(textAreaPanel) = BorderPanel.Position.Center
  layout(textFieldPanel) = BorderPanel.Position.South
  border = Swing.EmptyBorder(20, 20, 20, 20)
}

object FocusConceptsDemo {
  def main(args: Array[String]): Unit = {
     
    /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch {
        case ex: javax.swing.UnsupportedLookAndFeelException =>
            ex.printStackTrace()
        case ex: IllegalAccessException =>
            ex.printStackTrace()
        case ex: InstantiationException =>
            ex.printStackTrace()
        case ex: ClassNotFoundException =>
            ex.printStackTrace()
        }
        /* Turn off metal's use of bold fonts */
        javax.swing.UIManager.put("swing.boldMetal", false);
     
     
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            def run(): Unit = {
                createAndShowGUI()
            }
        });
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private def createAndShowGUI(): Unit = {
        //Create and set up the window.
        val frame = new Frame() {
          title = "FocusConceptsDemo"
          override def closeOperation(): Unit = {
            sys.exit(0)
          }
        }
 
        //Create and set up the content pane.
        val newContentPane = new FocusConceptsDemo()
        newContentPane.opaque = true   //content panes must be opaque
        frame.contents = newContentPane
 
        //Display the window.
        frame.pack()
        frame.visible = true
    }
}