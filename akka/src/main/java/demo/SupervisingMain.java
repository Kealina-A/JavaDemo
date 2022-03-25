package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class SupervisingMain {

    public static void main (String[] args) {
        ActorSystem system = ActorSystem.create("testSystem");
        ActorRef supervisingActor = system.actorOf(SupervisingActor.props(), "supervising-actor");
        supervisingActor.tell("failChild", ActorRef.noSender());

    }
}
