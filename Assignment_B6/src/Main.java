import com.mongodb.client.*;
import org.json.*;
import java.util.Iterator;
import java.util.Scanner;

import com.mongodb.*;
import org.bson.Document;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			//MongoClient mongo = new MongoClient("localhost",27017);
			MongoClient mongo = MongoClients.create("mongodb://localhost:27017");
			MongoDatabase db = mongo.getDatabase("assignment_b6");
			MongoCollection<Document> movie = db.getCollection("movie");
			Scanner sc = new Scanner(System.in);
			JSONObject obj = new JSONObject();
			JSONArray arr = new JSONArray();
			JSONObject sdoc = new JSONObject();
			System.out.println("ID: ");
			Integer id = sc.nextInt();
			obj.put("_id", id);
			System.out.println("Name: ");
			String name = sc.next();
			obj.put("name", name);
			char ch = 'y';
			while(ch == 'y')
			{
				System.out.println("Movie_Id:");
				int movie_id = sc.nextInt();
				sdoc.put("movie_id",movie_id);
				System.out.println("Genre:");
				String genre = sc.next();
				sdoc.put("genre",genre);
				System.out.println("Rating:");
				float rating = sc.nextFloat();
				sdoc.put("rating",rating);
				arr.put(sdoc);
				System.out.println("Continue? <y/n>");
				ch = sc.next().charAt(0);
			}
			obj.put("movies", arr);
			Document doc = Document.parse(obj.toString());
			movie.insertOne(doc);
			FindIterable<Document> iterDoc = movie.find();
			int i = 1;
			// Getting the iterator
			Iterator it = iterDoc.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
				i++;
			}
			mongo.close();
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}
