package com.linayi.entity.promoter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManRewardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public OrderManRewardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOrderManRewardIdIsNull() {
            addCriterion("order_man_reward_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdIsNotNull() {
            addCriterion("order_man_reward_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdEqualTo(Integer value) {
            addCriterion("order_man_reward_id =", value, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdNotEqualTo(Integer value) {
            addCriterion("order_man_reward_id <>", value, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdGreaterThan(Integer value) {
            addCriterion("order_man_reward_id >", value, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_man_reward_id >=", value, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdLessThan(Integer value) {
            addCriterion("order_man_reward_id <", value, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_man_reward_id <=", value, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdIn(List<Integer> values) {
            addCriterion("order_man_reward_id in", values, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdNotIn(List<Integer> values) {
            addCriterion("order_man_reward_id not in", values, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdBetween(Integer value1, Integer value2) {
            addCriterion("order_man_reward_id between", value1, value2, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManRewardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_man_reward_id not between", value1, value2, "orderManRewardId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdIsNull() {
            addCriterion("order_man_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderManIdIsNotNull() {
            addCriterion("order_man_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderManIdEqualTo(Integer value) {
            addCriterion("order_man_id =", value, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdNotEqualTo(Integer value) {
            addCriterion("order_man_id <>", value, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdGreaterThan(Integer value) {
            addCriterion("order_man_id >", value, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_man_id >=", value, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdLessThan(Integer value) {
            addCriterion("order_man_id <", value, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_man_id <=", value, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdIn(List<Integer> values) {
            addCriterion("order_man_id in", values, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdNotIn(List<Integer> values) {
            addCriterion("order_man_id not in", values, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdBetween(Integer value1, Integer value2) {
            addCriterion("order_man_id between", value1, value2, "orderManId");
            return (Criteria) this;
        }

        public Criteria andOrderManIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_man_id not between", value1, value2, "orderManId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdIsNull() {
            addCriterion("reward_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdIsNotNull() {
            addCriterion("reward_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdEqualTo(Integer value) {
            addCriterion("reward_rule_id =", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdNotEqualTo(Integer value) {
            addCriterion("reward_rule_id <>", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdGreaterThan(Integer value) {
            addCriterion("reward_rule_id >", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_rule_id >=", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdLessThan(Integer value) {
            addCriterion("reward_rule_id <", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("reward_rule_id <=", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdIn(List<Integer> values) {
            addCriterion("reward_rule_id in", values, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdNotIn(List<Integer> values) {
            addCriterion("reward_rule_id not in", values, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdBetween(Integer value1, Integer value2) {
            addCriterion("reward_rule_id between", value1, value2, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_rule_id not between", value1, value2, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andActualAmountIsNull() {
            addCriterion("actual_amount is null");
            return (Criteria) this;
        }

        public Criteria andActualAmountIsNotNull() {
            addCriterion("actual_amount is not null");
            return (Criteria) this;
        }

        public Criteria andActualAmountEqualTo(Long value) {
            addCriterion("actual_amount =", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountNotEqualTo(Long value) {
            addCriterion("actual_amount <>", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountGreaterThan(Long value) {
            addCriterion("actual_amount >", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("actual_amount >=", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountLessThan(Long value) {
            addCriterion("actual_amount <", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountLessThanOrEqualTo(Long value) {
            addCriterion("actual_amount <=", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountIn(List<Long> values) {
            addCriterion("actual_amount in", values, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountNotIn(List<Long> values) {
            addCriterion("actual_amount not in", values, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountBetween(Long value1, Long value2) {
            addCriterion("actual_amount between", value1, value2, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountNotBetween(Long value1, Long value2) {
            addCriterion("actual_amount not between", value1, value2, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeIsNull() {
            addCriterion("settlement_time is null");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeIsNotNull() {
            addCriterion("settlement_time is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeEqualTo(Date value) {
            addCriterion("settlement_time =", value, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeNotEqualTo(Date value) {
            addCriterion("settlement_time <>", value, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeGreaterThan(Date value) {
            addCriterion("settlement_time >", value, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("settlement_time >=", value, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeLessThan(Date value) {
            addCriterion("settlement_time <", value, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeLessThanOrEqualTo(Date value) {
            addCriterion("settlement_time <=", value, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeIn(List<Date> values) {
            addCriterion("settlement_time in", values, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeNotIn(List<Date> values) {
            addCriterion("settlement_time not in", values, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeBetween(Date value1, Date value2) {
            addCriterion("settlement_time between", value1, value2, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andSettlementTimeNotBetween(Date value1, Date value2) {
            addCriterion("settlement_time not between", value1, value2, "settlementTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}