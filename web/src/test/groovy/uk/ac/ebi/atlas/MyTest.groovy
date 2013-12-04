package uk.ac.ebi.atlas

import org.junit.Test

import static org.hamcrest.CoreMatchers.containsString
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class LanguageTest {
    def languages = [tom:['Java', 'Groovy'], dick:['C#', 'Ruby']]

    @Test void tomKnowsJavaAndGroovyHamcrest() {
        println("tomKnowsJavaAndGroovyHamcrest")
        assertThat languages['tom'], is(["Java", "Groovy"])
        assertThat languages['tom'][0], containsString("v")
        assertThat languages['tom'][1], containsString("v")
    }

}