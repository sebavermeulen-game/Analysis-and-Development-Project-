import { apiBaseUrl } from "../config/config.js";

/**
 * Creates a query parameter string from an object.
 * @param {Object} [queryParameters={}]
 * @returns {string}
 */
function createQueryParamString(queryParameters = {}) {
    const searchParams = new URLSearchParams(queryParameters);
    const queryString = searchParams.toString();
    return queryString ? `?${queryString}` : '';
}

/**
 * Creates request headers, including the Authorization header if a user token is available.
 * User token is disabled.
 * @returns {Object}
 */
function createRequestHeader() {
    return {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    };
}

/**
 * Makes an HTTP GET request.
 * @param {string} endpoint
 * @param {Object} [queryParameters]
 * @returns {Promise<Object>}
 */
function get(endpoint, queryParameters) {
    const requestOptions = {
        method: 'GET',
        headers: createRequestHeader(),
    };

    return call(endpoint, queryParameters, requestOptions);
}

/**
 * Makes an HTTP POST request.
 * @param {string} endpoint
 * @param {Object} [body]
 * @returns {Promise<Object>}
 */
function post(endpoint, body) {
    const requestOptions = {
        method: 'POST',
        headers: createRequestHeader(),
        body: body ? JSON.stringify(body) : undefined,
    };

    return call(endpoint, {}, requestOptions);
}

/**
 * Makes an HTTP PUT request.
 * @param {string} endpoint
 * @param {Object} [body]
 * @returns {Promise<Object>}
 */
function put(endpoint, body) {
    const requestOptions = {
        method: 'PUT',
        headers: createRequestHeader(),
        body: body ? JSON.stringify(body) : undefined,
    };

    return call(endpoint, {}, requestOptions);
}

/**
 * Makes an HTTP DELETE request.
 * @param {string} endpoint
 * @returns {Promise<Object>}
 */
function del(endpoint) {
    const requestOptions = {
        method: 'DELETE',
        headers: createRequestHeader(),
    };

    return call(endpoint, {}, requestOptions);
}

/**
 * Makes an HTTP PATCH request.
 * @param {string} endpoint
 * @param {Object} [body]
 * @returns {Promise<Object>}
 */
function patch(endpoint, body) {
    const requestOptions = {
        method: 'PATCH',
        headers: createRequestHeader(),
        body: body ? JSON.stringify(body) : undefined,
    };

    return call(endpoint, {}, requestOptions);
}

/**
 * Makes an HTTP request to the specified endpoint.
 * @param {string} endpoint
 * @param {Object} queryParameters
 * @param {Object} requestOptions
 * @returns {Promise<Object>}
 */
async function call(endpoint, queryParameters, requestOptions) {
    const queryParamString = createQueryParamString(queryParameters);
    const fullUri = apiBaseUrl + endpoint + queryParamString;

    try {
        const response = await fetch(fullUri, requestOptions);
        if (!response.ok) {
            console.error(`HTTP error! Status: ${response.status}`);
            return;
        }
        const text = await response.text();
        if (!text) {
            return null;
        }
        return JSON.parse(text);
    } catch (err) {
        console.error(`An error occurred while fetching data from the API:\nFull URI: ${fullUri}`, err);
        throw err;
    }
}

export { get, post, put, del, patch };
