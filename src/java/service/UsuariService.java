package service;

import authn.Secured;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import model.entities.Usuaris;

/**
 *
 * @author Jialiang Chen
 */
@Stateless
@Path("/rest/api/v1/customer")
public class UsuariService extends AbstractFacade<Usuaris> {
    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public UsuariService() {
        super(Usuaris.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers(){
        List<Usuaris> customers = super.findAll();
        List<Usuaris> u = new LinkedList<Usuaris>();
        for(Usuaris c : customers){
            Usuaris.add(new Usuaris(c));
        }
        return Response.ok(Usuaris).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuari(@PathParam("id") Long id){
        Usuaris u = em.find(Usuaris.class, id);
        //Only 1 customer may be found with the ID.
        if (u!=null){
            Usuaris u = new Usuaris(u.getUsuari());
            return Response.status(Response.Status.OK).entity(Usuaris).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer with this id does not exist.").build();
    }
    
    @PUT
    @Path("/{id}")
    @Secured
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putUsuari(@PathParam("id") Long id, Usuaris c){
        Usuaris u = em.find(Usuaris.class, id);
        if (u==null){
            //Control info provided, if new customer, password and username is required.
            if(c.getPassword()==null || c.getUsername()==null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Missing necessary information to create customer.").build();
            }else{
                em.persist(c);
            }
        }else{
            //Edit customer with provided info.
            super.edit(c);
        }
        return Response.ok().entity(new Usuaris(c)).build();
    }
}