package jp.co.stnet.cms.domain.common.search;




import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ja.JapaneseTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;


public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {


    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {

        context.analyzer("japanese").custom()
                .tokenizer(JapaneseTokenizerFactory.class)
                    .param("mode", "search")
                    .param("userDictionary", "META-INF/dict/userdict_ja.txt")
                .tokenFilter( LowerCaseFilterFactory.class )
                .tokenFilter( ASCIIFoldingFilterFactory.class );

    }
}
