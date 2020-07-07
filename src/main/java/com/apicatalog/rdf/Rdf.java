package com.apicatalog.rdf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.apicatalog.jsonld.lang.BlankNode;
import com.apicatalog.jsonld.uri.UriUtils;
import com.apicatalog.rdf.io.RdfFormat;
import com.apicatalog.rdf.io.RdfReader;
import com.apicatalog.rdf.io.RdfWriter;
import com.apicatalog.rdf.io.error.UnsupportedFormatException;
import com.apicatalog.rdf.lang.XsdConstants;
import com.apicatalog.rdf.spi.RdfProvider;

public final class Rdf {

    private Rdf() {
    }
    
    public static final RdfGraph createGraph() {
        return RdfProvider.provider().createGraph();
    }

    public static final RdfReader createReader(Reader reader, RdfFormat format) throws UnsupportedFormatException {

        if (reader == null || format == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createReader(reader, format);
    }
    
    public static final RdfReader createReader(InputStream is, RdfFormat format) throws UnsupportedFormatException {
        
        if (is == null || format == null) {
            throw new IllegalArgumentException();
        }

        return createReader(new InputStreamReader(is), format);
    }

    public static final RdfWriter createWriter(Writer writer, RdfFormat format) throws UnsupportedFormatException {
        
        if (writer == null || format == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createWriter(writer, format);
    }

    public static final RdfWriter createWriter(OutputStream os, RdfFormat format) throws UnsupportedFormatException {
        
        if (os == null || format == null) {
            throw new IllegalArgumentException();
        }

        return createWriter(new OutputStreamWriter(os), format);
    }

    public static final RdfDataset createDataset() {
        return RdfProvider.provider().createDataset();
    }

    public static final RdfTriple createTriple(RdfResource subject, RdfResource predicate, RdfValue object) {
        
        if (subject == null || predicate == null || object == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createTriple(subject, predicate, object);
    }

    public static final RdfNQuad createNQuad(RdfResource subject, RdfResource predicate, RdfValue object, RdfResource graphName) {
        
        if (subject == null) {            
            throw new IllegalArgumentException("Subject cannot be null.");
        }
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null.");
        }
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null.");            
        }

        return RdfProvider.provider().createNQuad(subject, predicate, object, graphName);
    }

    public static final RdfNQuad createNQuad(RdfTriple triple, RdfResource graphName) {
        
        if (triple == null) {            
            throw new IllegalArgumentException("Triple cannot be null.");
        }

        return RdfProvider.provider().createNQuad(triple.getSubject(), triple.getPredicate(), triple.getObject(), graphName);
    }

    public static RdfValue createValue(String value) {
        
        if (value == null) {
            throw new IllegalArgumentException();
        }
        
        if (UriUtils.isAbsoluteUri(value)) {
            return RdfProvider.provider().createIRI(value);
        }
        
        if (BlankNode.isWellFormed(value)) {
            return RdfProvider.provider().createBlankNode(value);
        }
        
        return RdfProvider.provider().createTypedString(value, XsdConstants.STRING);
    }

    public static RdfLiteral createString(String lexicalForm) {
        
        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }
        
        return RdfProvider.provider().createTypedString(lexicalForm, XsdConstants.STRING);
    }

    public static RdfLiteral createTypedString(String lexicalForm, String dataType) {
        
        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }
        
        return RdfProvider.provider().createTypedString(lexicalForm, dataType);
    }
    
    public static RdfLiteral createLangString(String lexicalForm, String langTag) {
        
        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }
        
        return RdfProvider.provider().createLangString(lexicalForm, langTag);
    }

    /**
     * Create a new {@link RdfResource}.
     * 
     * @param resource is an absolute IRI or blank node identifier
     * @return {@link RdfGraphName}
     */
    public static RdfResource createResource(String resource) {
        
        if (resource == null) {
            throw new IllegalArgumentException("The resource value cannot be null.");
        }
        
        if (UriUtils.isAbsoluteUri(resource)) {
            return RdfProvider.provider().createIRI(resource);
        }
        
        if (BlankNode.isWellFormed(resource)) {
            return RdfProvider.provider().createBlankNode(resource);
        }
        
        throw new IllegalArgumentException("The resource must be an absolute IRI or blank node identifier, but was [" + resource + "].");        
    }
    
    public static RdfResource createBlankNode(final String value) {
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createBlankNode(value);
    }
    
    public static RdfResource createIRI(final String value) {
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createIRI(value);
    }
}