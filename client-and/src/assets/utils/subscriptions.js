import { get } from "../utils/api/api-methods.js";

// Constants to replace magic numbers
const MAX_RANGE_METERS_FOR_MULTIPLE_LOCATIONS = 100000;
const MULTIPLE_LOCATIONS_VALUE = 5;
const SINGLE_LOCATION_VALUE = 1;
const UNLIMITED_HOURS = -1;
const DEFAULT_PRICE_UNIT = "ADCO/month";
const RANGE_UNIT = "m";
const TIME_UNIT = "hours";

const formatRange = (rangeMeters) => `${rangeMeters} ${RANGE_UNIT}`;
const formatDuration = (hours) => (hours === UNLIMITED_HOURS ? "Unlimited" : `${hours} ${TIME_UNIT}`);
const formatPrice = (price) => `${price} ${DEFAULT_PRICE_UNIT}`;

const calculateMultipleLocations = (rangeMeters) =>
    (rangeMeters > MAX_RANGE_METERS_FOR_MULTIPLE_LOCATIONS ? MULTIPLE_LOCATIONS_VALUE : SINGLE_LOCATION_VALUE);

const formatPlan = (plan) => ({
    subscriptionId: plan.subscriptionId,
    name: plan.subscriptionPlan,
    range: formatRange(plan.range),
    duration: formatDuration(plan.hours),
    intensity: plan.intensity,
    acceptLightingRequest: Boolean(plan.requestInformation),
    customizeSchedule: Boolean(plan.extraInformation),
    intensityControl: plan.intensity !== "Low",
    radius: formatRange(plan.range),
    price: formatPrice(plan.price),
    multipleLocations: calculateMultipleLocations(plan.range),
});

const mapApiResponseToSubscriptionData = (apiSubscriptions) => {
    const ORDERED_KEYS = ["Individual User", "Businesses", "Government & Large Organisations"];
    return ORDERED_KEYS.reduce((acc, key) => {
        const plans = apiSubscriptions?.[key];
        if (plans) {
            acc[key] = {
                plans: plans.map(formatPlan),
            };
        }
        return acc;
    }, {});
};

let cachedSubscriptionData = null;

const fetchSubscriptions = async () => {
    try {
        const apiSubscriptions = await get("GetAllSubscriptions");
        cachedSubscriptionData = mapApiResponseToSubscriptionData(apiSubscriptions);
        return cachedSubscriptionData;
    } catch (error) {
        console.error("Error fetching subscriptions:", error);
        return {};
    }
};

export const getSubscriptionData = async () => {
    if (cachedSubscriptionData) {
        return cachedSubscriptionData;
    } else {
        return await fetchSubscriptions();
    }
};
