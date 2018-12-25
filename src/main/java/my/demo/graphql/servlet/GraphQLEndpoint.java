package my.demo.graphql.servlet;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import my.demo.graphql.LinkRepository;
import my.demo.graphql.Mutation;
import my.demo.graphql.Query;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepository linkRepository;

    static {
        linkRepository = new LinkRepository();
    }

    public GraphQLEndpoint() {
        super(buildSchema(linkRepository));
    }

   private static GraphQLSchema buildSchema(LinkRepository linkRepository) {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository), new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }
}