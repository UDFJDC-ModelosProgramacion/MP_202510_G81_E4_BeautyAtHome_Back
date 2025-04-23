package co.edu.udistrital.mdp.beautyathome.model;

public class Review {

    private Service service;
    private Client client;
    private String comment;
    private int score;

    public Review(Service service, Client client, String comment, int score){
        this.client = client;
        this.service = service;
        this.comment = comment;
        this.score = score;
    }

}
