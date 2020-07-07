package com.apicatalog.jsonld.processor;

import java.net.URI;

import javax.json.JsonArray;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.api.JsonLdErrorCode;
import com.apicatalog.jsonld.api.JsonLdOptions;
import com.apicatalog.jsonld.deseralization.JsonLdToRdf;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.flattening.NodeMap;
import com.apicatalog.jsonld.flattening.NodeMapBuilder;
import com.apicatalog.jsonld.loader.DocumentLoaderOptions;
import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfDataset;

/**
 * 
 * @see <a href="https://w3c.github.io/json-ld-api/#dom-jsonldprocessor-tordf">JsonLdProcessor.toRdf()</a>
 *
 */
public final class ToRdfProcessor {

    private ToRdfProcessor() {
    }

    public static final RdfDataset toRdf(final URI input, final JsonLdOptions options) throws JsonLdError {

        if (options.getDocumentLoader() == null) {
            throw new JsonLdError(JsonLdErrorCode.LOADING_DOCUMENT_FAILED, "Document loader is null. Cannot fetch [" + input + "].");
        }
        
        final DocumentLoaderOptions loaderOptions = new DocumentLoaderOptions();
        loaderOptions.setExtractAllScripts(options.isExtractAllScripts());

        final Document remoteDocument = options.getDocumentLoader().loadDocument(input, loaderOptions);

        if (remoteDocument == null) {
            throw new JsonLdError(JsonLdErrorCode.LOADING_DOCUMENT_FAILED);
        }
        
        return toRdf(remoteDocument, options);
    }

    public static final RdfDataset toRdf(Document input, final JsonLdOptions options) throws JsonLdError {

        final JsonLdOptions expansionOptions = new JsonLdOptions(options);
        
        expansionOptions.setProcessingMode(options.getProcessingMode());
        expansionOptions.setBase(options.getBase());
        expansionOptions.setExpandContext(options.getExpandContext());
        
        final JsonArray expandedInput = ExpansionProcessor.expand(input, expansionOptions, false);

        return JsonLdToRdf
                        .with(
                            NodeMapBuilder.with(expandedInput, new NodeMap()).build(),
                            Rdf.createDataset()
                            )
                        .produceGeneralizedRdf(options.isProduceGeneralizedRdf())
                        .rdfDirection(options.getRdfDirection())
                        .build();     
    }
}
