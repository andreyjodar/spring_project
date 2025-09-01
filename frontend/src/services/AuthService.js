import BaseService from './BaseService';

const USER_TOKEN = 'accessToken';
const USER_RESPONSE = 'userResponse';

class AuthService extends BaseService {

    constructor(){
        super("/api/auth");
    }

    async login(loginRequest) {
        try {
            const response = await this.api.post(`${this.endPoint}/login`, loginRequest);
            if(response.data && response.data.accessToken) {
                this.#storeToken(response.data.accessToken);
                this.#storeUser(response.data.userResponse);
            }
            return response;
        } catch (error) {
            console.error("Login error:", error);
            throw error;
        }
    }

    async register(registerRequest) {
        try {
            const response = await this.api.post(`${this.endPoint}/register`, registerRequest);
            if(response.data && response.data.accessToken) {
                this.#storeToken(response.data.accessToken);
                this.#storeUser(response.data.userResponse);
            }
            return response;
        } catch (error) {
            console.error("Register error:", error);
        }
    }

    async forgotPassword(forgotPasswordRequest) {
        try {
            const response = await this.api.post(`${this.endPoint}/forgot-password`, forgotPasswordRequest);
            return response;
        } catch (error) {
            console.error("Forgot password error:", error);
        }
    }

    async changePassword(changePasswordRequest) {
        try {
            const response = await this.api.post(`${this.endPoint}/change-password`, changePasswordRequest);
            return response;
        } catch (error) {
            console.error("Change password error:", error);
        }
    }

    logout() {
        this.#removeToken();
        this.#removeUser();
    }

    #storeToken(accessToken) {
        localStorage.setItem(USER_TOKEN, accessToken);
    }

    #removeToken() {
        localStorage.removeItem(USER_TOKEN);
    }

    #storeUser(user) {
        localStorage.setItem(USER_RESPONSE, JSON.stringify(user));
    }

    #removeUser() {
        localStorage.removeItem(USER_RESPONSE);
    }
}

export default AuthService;