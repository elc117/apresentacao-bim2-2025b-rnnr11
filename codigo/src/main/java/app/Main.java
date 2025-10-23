package app;
import io.javalin.Javalin;

abstract class SomeEntity {
    private String name;
    private String type;

    public SomeEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public abstract String interact(SomeEntity a);
}

class NoiseMaker extends SomeEntity {
    private String noise;

    public NoiseMaker(String name, String type, String noise){
        super(name, type);
        this.noise = noise;
    }

    public String getNoise(){
        return this.noise;
    }

    @Override
    public String interact(SomeEntity a){
        return getName() + " infernizou " + a.getName() + "!";
    }

    public String makingNoise(){
        return getName() + " faz " + getNoise();
    }
}

class CalmSomeone extends SomeEntity {
    public CalmSomeone(String name, String type){
        super(name, type);
    }

    @Override
    public String interact(SomeEntity a){
        return getName() + " interagiu com " + a.getName() + ".";
    }
}

public class Main {
    public static void runServer(SomeEntity[] entities){
        var app = Javalin.create()
                .get("/HWorld", ctx -> ctx.result("Hello World\n"))
                .get("/moto", ctx -> {
                    if (entities[0] instanceof NoiseMaker) {
                        ctx.result(((NoiseMaker) entities[0]).makingNoise()); // Chama o método se for NoiseMaker
                    } else {
                        ctx.result("Algo deu errado...\n");
                    }

                })
                .get("/panBebe", ctx -> ctx.result(entities[5].interact(entities[3]) + "\n"))
                .get("/pedPudim", ctx -> ctx.result(entities[1].interact(entities[4]) + "\n"))
                .start(7070);
    }

    public static void main(String[] args) {
        SomeEntity[] entities = { // 0- Motosserra 1- Pedra 2- Carro 3- Bebê 4- Pudim de Pão 5- Panela
                new NoiseMaker("a motosserra", "objeto", "randandan"),
                new CalmSomeone("a pedra", "objeto"),
                new NoiseMaker("o carro", "veículo", "vrumvrum"),
                new NoiseMaker("o bebê", "pessoa", "buábuá"),
                new CalmSomeone("o pudim de pão", "comida"),
                new NoiseMaker("a panela", "objeto", "clankclank")
        };

        runServer(entities);
    }
}