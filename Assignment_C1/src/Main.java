import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.util.*;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("assignment_c1");
        Document document = db.runCommand(new Document("buildInfo",1));
        System.out.println("MongoDB Version: "+document.getString("version"));
        Scanner sc = new Scanner(System.in);
        MongoCollection<Document> collection = db.getCollection("movie");;
        int choice=0;
        do {
        	System.out.println("------Menu------");
        	System.out.println("1.Create Collection\n2.Select Collection\n3.Insert Document\n4.Retreive Document\n5.Retreive Document using logical Operators\n6.Update Document\n7.Delete Document\n8.Drop Collection");
        	choice=Integer.parseInt(sc.next());
        	switch(choice) {
        		
        		case 1:{
        			System.out.println("Enter Collection name:");
        			String name=sc.next();
        			db.createCollection(name);
        			System.out.println("Collection Created");
        			break;
        		}
        		case 2:{
        			collection = db.getCollection("movie");
        			System.out.println("Collection movie selected successfully");
        			break;
    			}
        		case 3:{
        			System.out.println("Name:");
        			String name=sc.next();
        			System.out.println("Age:");
        			int age=sc.nextInt();
        			System.out.println("Movie_id:");
        			int movie_id=sc.nextInt();
        			System.out.println("Genre:");
        			String genre=sc.next();
        			System.out.println("Rating:");
        			float rating=sc.nextFloat();
        			Document d1 = new Document("name",name)
                		.append("age",age)
                		.append("movie_id",movie_id)
                		.append("genre",genre)
                		.append("rating", rating);
        			collection.insertOne(d1);
                break;
                }
                case 4:{
                	FindIterable<Document> iterDoc = collection.find();
                    int i = 1;
                    Iterator it = iterDoc.iterator();
                    while(it.hasNext()) {
                    	System.out.println(it.next());
                    	i++;
                    }
                    break;
                }
                case 5:{
                	FindIterable<Document> iterDoc = db.getCollection("movie").find(Filters.or
                			(
                				Filters.eq("genre","comedy"),
                				Filters.eq("name","Miti")
                			)
                		);
                
	                MongoCursor <Document> cursor = iterDoc.iterator();
	                try {
	                	while(cursor.hasNext()) {
	                		System.out.println(cursor.next());
	                	}
	                }finally {
	                	cursor.close();
	                }
	                
	                iterDoc = db.getCollection("movie").find(Filters.and
	            			(
	            				Filters.gt("rating",6),
	            				Filters.lt("rating",8)
	            			)
	            		);
	            
	                cursor = iterDoc.iterator();
		            try {
		            	while(cursor.hasNext()) {
		            		System.out.println(cursor.next());
		            	}
		            }finally {
		            	cursor.close();
		            }
		            break;
		       }
                case 6:{
                	System.out.println("Name:");
                	String name=sc.next();
                	System.out.println("Field to be updated");
                	String field=sc.next();
                	System.out.println("Value");
                	float value=sc.nextFloat();
                	collection.updateOne(Filters.eq("name",name),Updates.set(field,value));
                	System.out.println("Collection");
                	break;
                }
                case 7:{
                	System.out.println("Name:");
                	String name=sc.next();
                	 collection.deleteOne(Filters.eq("name",name));
                	 System.out.println("Deleted Successfully");
                	break;
                }
                case 8:{
                    collection.drop();
                    System.out.println("Dropped Successfully");
                	break;
                }
        	}
        }while(choice!=8);
    }
}