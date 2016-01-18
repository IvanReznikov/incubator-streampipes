import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.google.common.io.Resources;

import de.fzi.cep.sepa.model.client.Pipeline;
import de.fzi.cep.sepa.storage.controller.StorageManager;

/**
 * Created by robin on 31.10.14.
 */
public class TestPipelineStorage {

    //private static final Logger LOG = LoggerFactory.getLogger(TestPipelineStorage.class);


    public static void main(String[] args) throws IOException{
        URL url = Resources.getResource("TestJSON.json");
        Scanner scanner = new Scanner(new File(url.getPath()));
        //String json = scanner.useDelimiter("\\Z").next();
        scanner.close();
       

        //CouchDbClient dbClient = new CouchDbClient();
        //dbClient.save(json);
        
        
        //dbClient.shutdown();
        
        Pipeline pipeline = StorageManager.INSTANCE.getPipelineStorageAPI().getPipeline("fa95b256-b6b0-4600-b998-6df72a19fa5b");
        System.out.println(pipeline.getPipelineId());
        pipeline.setRunning(true);
       // System.out.println(pipeline.getRev());
      StorageManager.INSTANCE.getPipelineStorageAPI().updatePipeline(pipeline);
        
        pipeline = StorageManager.INSTANCE.getPipelineStorageAPI().getPipeline("fa95b256-b6b0-4600-b998-6df72a19fa5b");
    }

}
