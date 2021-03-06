/*
Copyright 2019 FZI Forschungszentrum Informatik

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.streampipes.connect.container.worker.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.streampipes.connect.container.worker.management.AdapterWorkerManagement;
import org.apache.streampipes.connect.rest.AbstractContainerResource;
import org.apache.streampipes.container.assets.AssetZipGenerator;
import org.apache.streampipes.container.util.AssetsUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Path("/api/v1/{username}/worker/protocols")
public class ProtocolResource extends AbstractContainerResource {

    private AdapterWorkerManagement adapterWorkerManagement;

    public ProtocolResource() {
        this.adapterWorkerManagement = new AdapterWorkerManagement();
    }


    @GET
    @Path("/{id}/assets")
    @Produces("application/zip")
    public Response getAssets(@PathParam("id") String id) {
        List<String> includedAssets = this.adapterWorkerManagement.getProtocol(id).declareModel().getIncludedAssets();
        try {
            return ok(new AssetZipGenerator(id, includedAssets).makeZip());
        } catch (IOException e) {
            e.printStackTrace();
            return fail();
        }
    }

    @GET
    @Path("/{id}/assets/icon")
    @Produces("image/png")
    public Response getIconAsset(@PathParam("id") String elementId) throws IOException {
        URL iconUrl = Resources.getResource(AssetsUtil.makeIconPath(elementId));
        return ok(Resources.toByteArray(iconUrl));
    }

    @GET
    @Path("/{id}/assets/documentation")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDocumentationAsset(@PathParam("id") String elementId) throws IOException {
        URL documentationUrl = Resources.getResource(AssetsUtil.makeDocumentationPath(elementId));
        return Resources.toString(documentationUrl, Charsets.UTF_8);
    }
}
