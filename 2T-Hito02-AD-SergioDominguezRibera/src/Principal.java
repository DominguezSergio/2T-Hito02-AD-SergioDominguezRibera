import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Principal {
	static String cadena;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
			.url("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/search/star%2520wars")
			.get()
			.addHeader("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
			.addHeader("x-rapidapi-key", "53f784fcdcmsh841985255c9da41p100097jsnb99a0b1623e2")
			.build();

		Response response = client.newCall(request).execute();
		
		//System.out.println(response.body().string());
		System.out.println("---------------------------------");
		
		String api = response.body().string().toString();
		System.out.println(api);
		FileWriter flwriter = null;
		String ruta = ".\\archivo.json";
        File archivo = new File(ruta);
        BufferedWriter bw = null;
        try {
        	//
        	flwriter = new FileWriter(archivo);
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			bfwriter.write(api);
			bfwriter.close();
        	//
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonParser parser = new JsonParser();
		FileReader fr = new FileReader(ruta);
		JsonElement datos = parser.parse(fr);
        cadena = "";
        //System.out.println(datos);
		//
        if (datos.isJsonObject()) {
            JsonObject obj = datos.getAsJsonObject();
            java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
            java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
            while (iter.hasNext()) {
                java.util.Map.Entry<String,JsonElement> entrada = iter.next();
                leer2(entrada.getValue());
            }
        }
        System.out.println(cadena);
        cadena = "";
	}
	
	private static void leer(JsonElement datos) {
		// TODO Auto-generated method stub
		if (datos.isJsonObject()) {
            JsonObject obj = datos.getAsJsonObject();
            java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
            java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
            while (iter.hasNext()) {
                java.util.Map.Entry<String,JsonElement> entrada = iter.next();
                if(entrada.getKey() .equals("id")) {
                	cadena = cadena + entrada.getKey() + ":" + entrada.getValue() + "\r" + "----\r";
                }else {
                	cadena = cadena + entrada.getKey() + ":" + entrada.getValue() + "\r";
                }
                
                leer(entrada.getValue());
            }
            System.out.println("vuelta");
		}
	}
	private static void leer2(JsonElement datos) {
		// TODO Auto-generated method stub
		System.out.println("Acceso a metodo leer\n");
		if (datos.isJsonObject()) {
			System.out.println("leyendo JsonObject\n");
			
			JsonObject objeto = datos.getAsJsonObject(); //Coge los datos de datos y los combierte en un objeto
			
			Set<Entry<String, JsonElement>> entradas = objeto.entrySet(); //mapea el objeto para poder iterarlo en un bucle
			Iterator<Entry<String, JsonElement>> iterador = entradas.iterator(); //lo mismo que arriba, van juntos
			
			while(iterador.hasNext()) {
				Entry<String, JsonElement> entrada = iterador.next(); //Set conjunto de entradas, sin set solo una entrada
				System.out.println("Clave " + entrada.getKey());
				System.out.println("Valor " + entrada.getValue());
				leer2(entrada.getValue());
			}
		}
		else if(datos.isJsonArray()){
			System.out.println("leyendo JsonArray\n");
			
			JsonArray array = datos.getAsJsonArray();
			Iterator<JsonElement> iter = array.iterator();
			System.out.println(array.size());
			
			while (iter.hasNext()) {
	            JsonElement entrada = iter.next();
	            leer2(entrada);
	        }
		}
		else if(datos.isJsonPrimitive()) {
			//System.out.println("Leyendo JsonPrimitive");
		}
		else if(datos.isJsonNull()) {
			//System.out.println("Leyendo JsonNull");
		} //Cierra el if
		
	} //cierra el metodo leer
	

}
