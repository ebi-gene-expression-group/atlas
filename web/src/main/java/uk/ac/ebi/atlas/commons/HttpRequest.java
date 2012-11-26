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

package uk.ac.ebi.atlas.commons;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Named("httpRequest")
public class HttpRequest {

    public static InputStream httpPost(org.apache.http.client.HttpClient httpClient, String url, List<? extends NameValuePair> params) throws IOException {
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse response = httpClient.execute(httppost);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new IOException("Server returned invalid response: [status_code = " + statusCode + "; url = " + url + "]");
        }
        return response.getEntity().getContent();
    }

}
