package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.core.file.OpenOptions;
import io.vertx.mutiny.core.Vertx;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Path("/download")
public class DownloadResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadResource.class);
    private static final String TEST_FILE = System.getProperty("java.io.tmpdir") + File.separator + "test.txt";
    private static final OpenOptions READ_ONLY = new OpenOptions()
        .setWrite(false)
        .setCreate(false);


    @Inject
    Vertx vertx;

    @POST
    @Path("/create")
    public Uni<Response> createFile() {
        LOGGER.info("Creating test file: {}", TEST_FILE);
        return vertx.fileSystem()
            .createFile(TEST_FILE)
            .onItem().transform(it -> Response.noContent().build());
    }

    @DELETE
    @Path("/delete")
    public Uni<Response> deleteFile() {
        LOGGER.info("Deleting test file: {}", TEST_FILE);
        return vertx.fileSystem()
            .delete(TEST_FILE)
            .onItem().transform(it -> Response.noContent().build());
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Uni<Response> downloadFile() {
        LOGGER.info("Downloading test file: {}", TEST_FILE);
        return vertx.fileSystem()
            .open(TEST_FILE, READ_ONLY)
            .onItem().transform(it -> Response.ok(it).build());
    }
}
