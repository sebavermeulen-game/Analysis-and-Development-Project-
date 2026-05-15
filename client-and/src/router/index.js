import {createRouter, createWebHistory} from 'vue-router';
import HomeView from '../views/HomeView.vue';
import Reviews from '../views/Reviews.vue';
import MainView from "../views/MainView.vue";
import RangeExtensionView from "../views/RangeExtensionView.vue";
import AccountView from "../views/AccountView.vue";
import TopUpView from "../views/TopUpView.vue";
import SubscriptionPage from "../views/SubscriptionPage.vue";
import DeployView from "../views/DeployView.vue";
import SuccessfulDeployOrderView from "../views/SuccessfulDeployOrderView.vue";
import SuccessfulSubscriptionOrderView from "../views/SuccessfulSubscriptionOrderView.vue";
import ContactView from "../views/ContactView.vue";
import FAQView from "../views/FAQView.vue";

import SubscriptionConfirmationView from '@/views/SubscriptionConfirmationView.vue';


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [

        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/main',
            name: 'main',
            component: MainView
        },
        {
            path: '/account',
            name: 'account',
            component: AccountView
        },
        {
            path: '/top-up',
            name: 'top-up',
            component: TopUpView
        },
        {
            path: '/range-extension',
            name: 'range-extension',
            component: RangeExtensionView
        },
        {
            path: '/subscriptions',
            name: 'subscriptions',
            component: SubscriptionPage
        },
        {
            path: '/subscription-confirmation',
            name: 'SubscriptionConfirmation',
            component: SubscriptionConfirmationView,
            props: route => ({ selectedPlan: route.state?.selectedPlan }),
        },
        {
            path: '/reviews',
            name: 'Reviews',
            component: Reviews
        },
        {
            path: '/successful-deploy-order',
            name: 'Successful-Deploy-Order',
            component: SuccessfulDeployOrderView
        },
        {
            path: '/successful-subscription-order',
            name: 'Successful-Subscription-Order',
            component: SuccessfulSubscriptionOrderView,
        },
        {
            path: '/deploy',
            name: 'deploy',
            component: DeployView
        },
        {
            path: '/contact',
            name: 'contact',
            component: ContactView
        },
        {
            path: '/faq',
            name: 'faq',
            component: FAQView
        }
    ]
});

export default router;