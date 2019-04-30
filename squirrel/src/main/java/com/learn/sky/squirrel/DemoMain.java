package com.learn.sky.squirrel;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

/**
 * @Date: 2018/5/4 下午5:50
 */
public class DemoMain {

    public static void main(String[] args) {
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(DemoMachineSpamle.class);
        UntypedStateMachine fsm = builder.newAnyStateMachine(DemoState.BEGIN);
        DemoContext context = new DemoContext(4, "jiubukong", 100);
        fsm.fire(DemoEvent.LEADER_AGREE, context);
        fsm.fire(DemoEvent.FINANCE_AGREE, context);
        fsm.fire(DemoEvent.HR_NOT_AGREE, context);
        System.out.println("Current state is " + fsm.getCurrentState());
    }

}
