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
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES LOSS OF USE, DATA, OR
 * PROFITS OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scala.swing.examples.tutorials.components

import scala.swing._
import scala.swing.event.Key
import java.net.URL
import javax.swing.{ ImageIcon, KeyStroke }
import java.awt.event.{ ActionEvent, KeyEvent }

/*
 * Tutorial: How to Use Menus
 * http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuLookDemoProject/src/components/MenuLookDemo.java
 *
 * MenuLookDemo.scala requires /scala/swing/examples/tutorials/images/middle.gif.
 *
 * This class exists solely to show you what menus look like.
 * It has no menu-related event handling.
 */
class MenuLookDemo extends BorderPanel {
  val output = new TextArea(5, 30)
  val scrollPane = new ScrollPane(output)
  layout(scrollPane) = BorderPanel.Position.Center

  def createMenuBar: MenuBar = {
    //Create the menu bar.
    val menuBar = new MenuBar()
    //Build the fist menu.
    val menu = new Menu("A Menu") {
      mnemonic = Key.A
    }
    menu.peer.getAccessibleContext().setAccessibleDescription(
      "The only menu in this program that has menu items")
    menuBar.menus :+ menu
    //a group of JMenuItems
    val menuItem1 = new MenuItem(new Action("A text-only menu item") {
      def apply() = {}
      accelerator = Some(KeyStroke.getKeyStroke(
        KeyEvent.VK_1, ActionEvent.ALT_MASK))
      longDescription = "This doesn't really do anything"
    }) {
      mnemonic = Key.T
    }
    menu.contents += menuItem1
    //         
    val icon: ImageIcon = MenuDemo.createImageIcon("/scala/swing/examples/tutorials/images/middle.gif")
    val menuItem2 = new MenuItem("Both text and icon") {
      icon = icon
      mnemonic = Key.B
    }
    menu.contents += menuItem2
    //         
    val menuItem3 = new MenuItem("") {
      icon = icon
      mnemonic = Key.D
    }
    //a group of radio button menu items
    menu.contents += new Separator(Orientation.Horizontal)
    val group = new ButtonGroup()
    //
    val rbMenuItem1 = new RadioMenuItem("A radio button menu item") {
      selected = true
      mnemonic = Key.R
    }
    group.buttons += rbMenuItem1
    menu.contents += rbMenuItem1
    //
    val rbMenuItem2 = new RadioMenuItem("Another one") {
      mnemonic = Key.O
    }
    group.buttons += rbMenuItem2
    menu.contents += rbMenuItem2
    //a group of check box menu items
    menu.contents += new Separator(Orientation.Horizontal)
    val cbMenuItem1 = new CheckMenuItem("A check box menu item") {
      mnemonic = Key.C
    }
    menu.contents += cbMenuItem1
    val cbMenuItem2 = new CheckMenuItem("Another one") {
      mnemonic = Key.H
    }
    //
    //a submenu
    menu.contents += new Separator(Orientation.Horizontal)
    val submenu = new Menu("A submenu") {
      mnemonic = Key.S
    }
    val menuItemS1 = new MenuItem(new Action("An item in the submenu") {
      def apply() = {}
      accelerator = Some(KeyStroke.getKeyStroke(
        KeyEvent.VK_2, ActionEvent.ALT_MASK))
    })
    submenu.contents += menuItemS1
    //
    val menuItemS2 = new MenuItem("Another item")
    submenu.contents += menuItemS2
    menu.contents += submenu
    //
    //Build second menu in the menu bar.
    val menu2 = new Menu("Another Menu") {
      mnemonic = Key.N
    }
    menu2.peer.getAccessibleContext().setAccessibleDescription(
      "This menu does nothing")
    menuBar.contents += menu
    menuBar.contents += menu2
    menuBar
  }

  def actionPerformed(source: MenuItem) {
    val newline = "\n"
    val s = "Action event detected." +
      newline +
      "    Event source: " + source.text +
      " (an instance of " + getClassName(source) + ")";
    output.append(s + newline);
    output.caret.position = output.peer.getDocument().getLength()
  }

  def itemStateChanged(source: MenuItem) {
    val newline = "\n"
    val s = "Item event detected." +
      newline +
      "    Event source: " + source.text +
      " (an instance of " + getClassName(source) + ")" +
      newline +
      "    New state: " +
      (if (source.selected) "selected" else "unselected")
    output.append(s + newline);
    output.caret.position = output.peer.getDocument().getLength()
  }

  // Returns just the class name -- no package info.
  def getClassName(o: AnyRef) {
    val classString = o.getClass().getName()
    val dotIndex = classString.lastIndexOf(".")
    classString.substring(dotIndex + 1)
  }
}

object MenuLookDemo {
  /** Returns an ImageIcon, or null if the path was invalid. */
  def createImageIcon(path: String): ImageIcon = {
    val imgURL: URL = getClass().getResource(path)
    if (imgURL != null) {
      Swing.Icon(imgURL)
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
    //Create and set up the window.
    val frame: Frame = new Frame() {
      title = "MenuLookDemo"
      override def closeOperation() = {
        sys.exit(0)
      }
    }
    //Create and set up the content pane.
    val newContentPane = new MenuLookDemo() {
      opaque = true
    }
    frame.menuBar = newContentPane.createMenuBar
    frame.contents = newContentPane

    //Display the window.
    frame.pack()
    frame.visible = true
  }

  def main(args: Array[String]): Unit = {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      def run(): Unit = {
        createAndShowGUI()
      }
    })
  }
}