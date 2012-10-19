/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.utils.biomart;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientHelper {
    public static InputStream httpGet(HttpClient httpClient, URI uri) throws IOException {
        HttpGet httpget = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpget);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new IOException("Server returned invalid response: [status_code = " + statusCode + "; url = " + uri + "]");
        }
        return response.getEntity().getContent();
    }

    public static InputStream httpPost(HttpClient httpClient, URI uri, List<? extends NameValuePair> params) throws IOException {
        HttpPost httppost = new HttpPost(uri);
        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse response = httpClient.execute(httppost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new IOException("Server returned invalid response: [status_code = " + statusCode + "; url = " + uri + "]");
        }
        return response.getEntity().getContent();
    }

    public static URI concatUri(URI url, final List<? extends NameValuePair> params) {
        List<NameValuePair> q = new ArrayList<NameValuePair>();
        q.addAll(URLEncodedUtils.parse(url, HTTP.UTF_8));
        q.addAll(params);

        try {
            return URIUtils.createURI(
                    url.getScheme(),
                    url.getHost(),
                    url.getPort(),
                    url.getPath(),
                    URLEncodedUtils.format(q, HTTP.UTF_8),
                    "");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to re-assemble BioMart url: origin = " + url + ", params = " + params, e);
        }
    }
}
