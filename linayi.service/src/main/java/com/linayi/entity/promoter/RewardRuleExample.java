package com.linayi.entity.promoter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RewardRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public RewardRuleExample() {
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

        public Criteria andRuleDescriptionIsNull() {
            addCriterion("rule__description is null");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionIsNotNull() {
            addCriterion("rule__description is not null");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionEqualTo(String value) {
            addCriterion("rule__description =", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionNotEqualTo(String value) {
            addCriterion("rule__description <>", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionGreaterThan(String value) {
            addCriterion("rule__description >", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("rule__description >=", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionLessThan(String value) {
            addCriterion("rule__description <", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionLessThanOrEqualTo(String value) {
            addCriterion("rule__description <=", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionLike(String value) {
            addCriterion("rule__description like", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionNotLike(String value) {
            addCriterion("rule__description not like", value, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionIn(List<String> values) {
            addCriterion("rule__description in", values, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionNotIn(List<String> values) {
            addCriterion("rule__description not in", values, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionBetween(String value1, String value2) {
            addCriterion("rule__description between", value1, value2, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andRuleDescriptionNotBetween(String value1, String value2) {
            addCriterion("rule__description not between", value1, value2, "ruleDescription");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeIsNull() {
            addCriterion("effective_type is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeIsNotNull() {
            addCriterion("effective_type is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeEqualTo(String value) {
            addCriterion("effective_type =", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeNotEqualTo(String value) {
            addCriterion("effective_type <>", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeGreaterThan(String value) {
            addCriterion("effective_type >", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeGreaterThanOrEqualTo(String value) {
            addCriterion("effective_type >=", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeLessThan(String value) {
            addCriterion("effective_type <", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeLessThanOrEqualTo(String value) {
            addCriterion("effective_type <=", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeLike(String value) {
            addCriterion("effective_type like", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeNotLike(String value) {
            addCriterion("effective_type not like", value, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeIn(List<String> values) {
            addCriterion("effective_type in", values, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeNotIn(List<String> values) {
            addCriterion("effective_type not in", values, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeBetween(String value1, String value2) {
            addCriterion("effective_type between", value1, value2, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTypeNotBetween(String value1, String value2) {
            addCriterion("effective_type not between", value1, value2, "effectiveType");
            return (Criteria) this;
        }

        public Criteria andRewardAmountIsNull() {
            addCriterion("reward_amount is null");
            return (Criteria) this;
        }

        public Criteria andRewardAmountIsNotNull() {
            addCriterion("reward_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRewardAmountEqualTo(Long value) {
            addCriterion("reward_amount =", value, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountNotEqualTo(Long value) {
            addCriterion("reward_amount <>", value, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountGreaterThan(Long value) {
            addCriterion("reward_amount >", value, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("reward_amount >=", value, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountLessThan(Long value) {
            addCriterion("reward_amount <", value, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountLessThanOrEqualTo(Long value) {
            addCriterion("reward_amount <=", value, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountIn(List<Long> values) {
            addCriterion("reward_amount in", values, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountNotIn(List<Long> values) {
            addCriterion("reward_amount not in", values, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountBetween(Long value1, Long value2) {
            addCriterion("reward_amount between", value1, value2, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardAmountNotBetween(Long value1, Long value2) {
            addCriterion("reward_amount not between", value1, value2, "rewardAmount");
            return (Criteria) this;
        }

        public Criteria andRewardIdIsNull() {
            addCriterion("reward_id is null");
            return (Criteria) this;
        }

        public Criteria andRewardIdIsNotNull() {
            addCriterion("reward_id is not null");
            return (Criteria) this;
        }

        public Criteria andRewardIdEqualTo(Integer value) {
            addCriterion("reward_id =", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotEqualTo(Integer value) {
            addCriterion("reward_id <>", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdGreaterThan(Integer value) {
            addCriterion("reward_id >", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_id >=", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdLessThan(Integer value) {
            addCriterion("reward_id <", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdLessThanOrEqualTo(Integer value) {
            addCriterion("reward_id <=", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdIn(List<Integer> values) {
            addCriterion("reward_id in", values, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotIn(List<Integer> values) {
            addCriterion("reward_id not in", values, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdBetween(Integer value1, Integer value2) {
            addCriterion("reward_id between", value1, value2, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_id not between", value1, value2, "rewardId");
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