/*
   Copyright (c) 2010 Diego Pizzocaro
   Permission is hereby granted, free of charge, to any person
   obtaining a copy of this software and associated documentation
   files (the "Software"), to deal in the Software without
   restriction, including without limitation the rights to use,
   copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the
   Software is furnished to do so, subject to the following
   conditions:

   The above copyright notice and this permission notice shall be
   included in all copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
   OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
   HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
   WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
   FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
   OTHER DEALINGS IN THE SOFTWARE.
   

 * ------------------------------------------------------------
 * "THE BEERWARE LICENSE" (Revision 42):
 * Diego Pizzocaro wrote this code. As long as you retain this 
 * notice, you can do whatever you want with this stuff. If we
 * meet someday, and you think this stuff is worth it, you can
 * buy me a beer in return.
 * ------------------------------------------------------------
 */

package GlobalMessenger;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.valueLayer.GridValueLayer;

import static repast.simphony.essentials.RepastEssentials.*;


/**
 * Global Messenger model.
 * 
 * @author Diego Pizzocaro
 * 
 */
public class GMContextCreator implements ContextBuilder {


	public Context build(Context context) {
		int xdim = 20;   // The x dimension of the physical space
		int ydim = 20;   // The y dimension of the physical space

		
		// Create a new 2D continuous space to model the physical space 
		ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
		.createContinuousSpace("ContinuousSpace2D", context, new RandomCartesianAdder(),
				new repast.simphony.space.continuous.StickyBorders() , xdim, ydim);

		
		NetworkFactoryFinder.createNetworkFactory(null).createNetwork("SensorNetwork", context, false);
						
		// The environment parameters contain the user-editable values that appear in the GUI.
		//  Get the parameters p and then specifically the initial numbers of wolves and sheep.
		Parameters p = RunEnvironment.getInstance().getParameters();
		int numNodes = (Integer)p.getValue("numNodes");
				

		// Populate the root context with the initial agents		
        GlobalMessenger theGlobalMessenger = new GlobalMessenger();
        context.add(theGlobalMessenger);
        theGlobalMessenger.initialize();
        System.out.println("init Global Messenger, adentID = "+theGlobalMessenger.agentID+", agentIDcounter = "+GlobalMessenger.agentIDCounter);

        for (int i = 1; i <= numNodes; i++) {

            Node aNode = new Node();
            context.add(aNode);
            aNode.initialize(theGlobalMessenger);
            System.out.println("Creating node, adentID = "+aNode.agentID+", agentIDcounter = "+Node.agentIDCounter);            

        }

               
		return context;                       
	}
}