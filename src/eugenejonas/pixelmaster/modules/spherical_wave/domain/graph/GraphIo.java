

package eugenejonas.pixelmaster.modules.spherical_wave.domain.graph;


import java.io.*;


/**
 * Class for reading graphs from files and writing them to files.
 */
public final class GraphIo
{
	public static void writeUnorientedGraph(UnorientedGraph graph, File outputFile) throws IOException
	{
		assert graph != null && outputFile != null;
		
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		out.println(graph.vertices.size() +" "+ graph.getEdgeCount());
		
		VertexIterator vertexIterator = graph.getVertexIterator();
		
		while (vertexIterator.hasNext())
		{
			Vertex v = vertexIterator.next();
			out.println(v.getX() +" "+ v.getY());
		}
		
		UnorientedEdgeIterator edgeIterator = graph.getEdgeIterator();
		
		while (edgeIterator.hasNext())
		{
			UnorientedEdge edge = edgeIterator.next();
			out.println(edge.firstVertex.getX() +" "+ edge.firstVertex.getY()
				+" "+ edge.secondVertex.getX() +" "+ edge.secondVertex.getY());
		}
		
		out.flush();
		out.close();
	}
	
	/**
	 * @return null if file has incorrect format.
	 */
	public static UnorientedGraph readUnorientedGraph(File inputFile) throws IOException
	{
		assert inputFile != null;
		
		
		try
		{
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			String[] lineSplit = line.split(" ", 2);
			
			if (lineSplit.length != 2)
			{
				return null;
			}
			
			int vertexCount = Integer.parseInt(lineSplit[0]);
			int edgeCount = Integer.parseInt(lineSplit[1]);
			
			if (vertexCount < 0 || edgeCount < 0 || vertexCount > 1000)
			{
				return null;
			}
			
			UnorientedGraph graph = new UnorientedGraph();
			
			for (int i = 0; i < vertexCount; i++)
			{
				line = br.readLine();
				lineSplit = line.split(" ", 2);
				
				if (lineSplit.length != 2)
				{
					return null;
				}
				
				int
					x = Integer.parseInt(lineSplit[0]),
					y = Integer.parseInt(lineSplit[1]);
				
				if (graph.addVertex(x, y) == null)
				{
					graph.clear();
					return null;
				}
			}
			
			for (int i = 0; i < edgeCount; i++)
			{
				line = br.readLine();
				lineSplit = line.split(" ", 4);
				
				if (lineSplit.length != 4)
				{
					return null;
				}
				
				int
					x1 = Integer.parseInt(lineSplit[0]),
					y1 = Integer.parseInt(lineSplit[1]),
					x2 = Integer.parseInt(lineSplit[2]),
					y2 = Integer.parseInt(lineSplit[3]);
				
				if (!graph.addEdge(x1, y1, x2, y2))
				{
					graph.clear();
					return null;
				}
			}
			
			br.close();
			
			return graph;
		}
		catch (NumberFormatException exc)		//note: unchecked exception
		{
			return null;
		}
	}
}
