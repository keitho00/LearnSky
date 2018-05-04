package com.learn.sky.squirrel;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @Author: wanghao
 * @Date: 2018/5/2 下午4:51
 */
public class QuickStartSample {
    // 1. Define State Machine DemoEvent
    enum FSMEvent {
        ToA, ToB, ToC, ToD
    }

    // 2. Define State Machine Class
    @StateMachineParameters(stateType = String.class, eventType = FSMEvent.class, contextType = Integer.class)
    static class StateMachineSample extends AbstractUntypedStateMachine {
        protected void fromAToB(String from, String to, FSMEvent event, Integer context) {
            System.out.println("fromAToB Transition from '" + from + "' to '" + to + "' on event '" + event +
                    "' with context '" + context + "'.");
        }

        protected void ontoB(String from, String to, FSMEvent event, Integer context) {
            System.out.println("Entry State \'" + to + "\'.");
        }

        protected void fromAToC(String from, String to, FSMEvent event, Integer context) {
            System.out.println("fromAToC Transition from '" + from + "' to '" + to + "' on event '" + event +
                    "' with context '" + context + "'.");
        }
        protected void fromBToC(String from, String to, FSMEvent event, Integer context) {
            System.out.println("fromBToC Transition from '" + from + "' to '" + to + "' on event '" + event +
                    "' with context '" + context + "'.");
        }
    }

    public static void main(String[] args) {
        // 3. Build State Transitions
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(StateMachineSample.class);
        builder.externalTransition().from("A").to("B").on(FSMEvent.ToB).callMethod("fromAToB");
        builder.externalTransition().from("A").to("C").on(FSMEvent.ToC).callMethod("fromAToC");
        builder.externalTransition().from("B").to("C").on(FSMEvent.ToC).callMethod("fromBToC");
        builder.onEntry("B").callMethod("ontoB");

        // 4. Use State Machine
        UntypedStateMachine fsm = builder.newStateMachine("B");
        fsm.fire(FSMEvent.ToB, 10);
        fsm.fire(FSMEvent.ToC, 1);

        System.out.println("Current state is " + fsm.getCurrentState());
    }
}
