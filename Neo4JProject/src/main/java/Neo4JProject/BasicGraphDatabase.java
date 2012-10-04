package Neo4JProject;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

/**
 * Hello world!
 * 
 */
public class BasicGraphDatabase {

	private static String DB_PATH = "db_path";
	private static final String USERNAME_KEY = "username";
	private GraphDatabaseService graphDb;
	private Index<Node> nodeIndex;

	public BasicGraphDatabase() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		nodeIndex = graphDb.index().forNodes("nodes");
		registerShutdownHook(graphDb);
	}

	// Find a user through the search index
	// START SNIPPET: findUser
	// int idToFind = 45;
	// Node foundUser = nodeIndex
	// .get(USERNAME_KEY, idToUserName(idToFind)).getSingle();
	// System.out.println("The username of user " + idToFind + " is "
	// + foundUser.getProperty(USERNAME_KEY));
	// // END SNIPPET: findUser
	//
	// // Delete the persons and remove them from the index
	// for (Relationship relationship : usersReferenceNode
	// .getRelationships(RelTypes.USER, Direction.OUTGOING)) {
	// Node user = relationship.getEndNode();
	// nodeIndex.remove(user, USERNAME_KEY,
	// user.getProperty(USERNAME_KEY));
	// user.delete();
	// //relationship.delete();
	// }
	// usersReferenceNode.getSingleRelationship(RelTypes.USERS_REFERENCE,
	// Direction.INCOMING).delete();
	// usersReferenceNode.delete();
	// tx.success();

	public Node createAndIndexNode(final String username) {
		Transaction tx = graphDb.beginTx();
		Node node = graphDb.createNode();
		node.setProperty(USERNAME_KEY, username);
		nodeIndex.add(node, USERNAME_KEY, username);
		tx.success();
		tx.finish();
		// graphDb.shutdown();
		return node;
	}

	private void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	public static enum RelTypes implements RelationshipType {
		USERS_REFERENCE, USER
	}

	public Node getNode(String property) {
		Node foundNode = nodeIndex.get(USERNAME_KEY, property).getSingle();
		return foundNode;
	}
}
