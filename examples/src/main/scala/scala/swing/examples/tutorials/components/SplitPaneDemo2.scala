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

package scala.swing.examples.tutorials.components

import scala.swing._
import scala.swing.event.SelectionChanged
import java.net.URL
import javax.swing.ImageIcon

/*
 * How to Use Split Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemo2Project/src/components/SplitPaneDemo2.java
 */
class SplitPaneDemo2 extends Frame {
  title = "SplitPaneDemo2"

  //Create an instance of SplitPaneDemo.
  val splitPaneDemo = new SplitPaneDemo()
  val top: SplitPane = splitPaneDemo.getSplitPane()
  val listMe: ListView[String] = splitPaneDemo.getImageList()
  
  //XXXX: Bug #4131528, borders on nested split panes accumulate.
  //Workaround: Set the border on any split pane within
  //another split pane to null. Components within nested split
  //panes need to have their own border for this to work well.
  top.border = null
  
  //Create a regular old label
  val label = new Label("Click on an image name in the list.") {
    horizontalAlignment = Alignment.Center
  }
  
  //Create a split pane and put "top" (a split pane)
        //and JLabel instance in it.
  val splitPane = new SplitPane(Orientation.Vertical, top, label) {
    oneTouchExpandable = true
    dividerLocation = 180
  }
  
  //Provide minimum sizes for the two components in the split pane
  top.minimumSize = new Dimension(100, 50)
  label.minimumSize = new Dimension(100, 30)
  
  //Add the split pane to this frame
  contents = splitPane
  
  listenTo(splitPaneDemo.getImageList().selection)
  
  reactions += {
    case (e: SelectionChanged) => 
        val theList: ListView[String] = e.source.asInstanceOf[ListView[String]]
        if (!theList.selection.adjusting) {
          if (theList.selection.leadIndex < 0)
            label.text = "Nothing selected."
        } else {
            val index = theList.selection.leadIndex
            label.text = "Selected image number " + index
        }
  }

}

object SplitPaneDemo2 {
  /** Returns an ImageIcon, or null if the path was invalid. */
  def createImageIcon(path: String): ImageIcon = {
    val imgURL: URL = getClass().getResource(path)
    if (imgURL != null) {
      // scala swing has no mechanism for setting the description.
      new javax.swing.ImageIcon(imgURL)
    } else {
      null
    }
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  def createAndShowGUI(): Unit = {
    val frame = new SplitPaneDemo2() {
      title = "SplitPaneDemo2"
      // Display the window
      pack()
      visible = true
      override def closeOperation() = {
         sys.exit(0)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      def run(): Unit = {
        javax.swing.UIManager.put("swing.boldMetal", false)
        createAndShowGUI()
      }
    })
  }
}

