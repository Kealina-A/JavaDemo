package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class StartStopMain {

    public static void main (String[] args) {
        ActorSystem system = ActorSystem.create("testSystem");
        ActorRef first = system.actorOf(StartStopActor1.props(), "first");
        first.tell("stop", ActorRef.noSender());
    }
}
