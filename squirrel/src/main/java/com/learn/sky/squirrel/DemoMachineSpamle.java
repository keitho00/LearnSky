package com.learn.sky.squirrel;

import com.google.gson.Gson;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @Author: wanghao
 * @Date: 2018/5/4 下午5:20
 */
@States({
        @State(name = "BEGIN"),
        @State(name = "LEADER_AGREE"),
        @State(name = "FINANCE_AGREE"),
        @State(name = "FINISH"),
        @State(name = "CANCEL"),
        @State(name = "BOSS_AGREE")
})
@Transitions({
        @Transit(from = "BEGIN", to = "LEADER_AGREE", on = "LEADER_AGREE", callMethod = "defaultCallMethod"),
        @Transit(from = "BEGIN", to = "FINISH", on = "LEADER_NOT_AGREE", callMethod = "defaultCallMethod"),
        @Transit(from = "BEGIN", to = "FINISH", on = "MIN_CANCEL", callMethod = "defaultCallMethod"),


        @Transit(from = "LEADER_AGREE", to = "FINANCE_AGREE", on = "FINANCE_AGREE", callMethod = "defaultCallMethod"
                , whenMvel = "MyCondition:::(context!=null && context.getLevel()<4)"),
        @Transit(from = "LEADER_AGREE", to = "BOSS_AGREE", on = "BOSS_AGREE", callMethod = "defaultCallMethod"
                , whenMvel = "MyCondition:::(context!=null && context.getLevel()>=4)"),
        @Transit(from = "LEADER_AGREE", to = "FINISH", on = "FINANCE_NOT_AGREE", callMethod = "defaultCallMethod"
                , whenMvel = "MyCondition:::(context!=null && context.getLevel()<4)"),
        @Transit(from = "LEADER_AGREE", to = "FINISH", on = "BOSS_NOT_AGREE", callMethod = "defaultCallMethod"
                , whenMvel = "MyCondition:::(context!=null && context.getLevel()>=4)"),

        @Transit(from = "BOSS_AGREE", to = "FINANCE_AGREE", on = "FINANCE_AGREE", callMethod = "defaultCallMethod"),
        @Transit(from = "BOSS_AGREE", to = "FINISH", on = "FINANCE_NOT_AGREE", callMethod = "defaultCallMethod"),

        @Transit(from = "FINANCE_AGREE", to = "FINISH", on = "HR_AGREE", callMethod = "defaultCallMethod"),
        @Transit(from = "FINANCE_AGREE", to = "CANCEL", on = "HR_NOT_AGREE", callMethod = "defaultCallMethod"),


})
@StateMachineParameters(stateType = DemoState.class, eventType = DemoEvent.class, contextType = DemoContext.class)
public class DemoMachineSpamle extends AbstractUntypedStateMachine {
    protected void defaultCallMethod(DemoState from, DemoState to, DemoEvent event, DemoContext context) {
        System.out.println("==========\nFrom:" + from.name() + "\nTo:" + to.name() + "\nOn:" + event.name() + "\nContext:" + new Gson().toJson(context));
    }

}
