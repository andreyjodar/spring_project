import BaseService from './BaseService';

const USER_TOKEN = 'token';
const USER_RESPONSE = 'user';

class AuthService extends BaseService {

    constructor(){
        super("/auth");
    }

    #storeToken(token) {
        localStorage.setItem(USER_TOKEN, token);
    }

    #removeToken() {
        localStorage.removeItem(USER_TOKEN);
    }

    #storeUser(user) {
        localStorage.setItem(USER_RESPONSE, user);
    }

    #removeUser() {
        localStorage.removeItem(USER_RESPONSE);
    }

    async login(loginRequest) {
        try {
            const response = await this.api.post(`${this.endPoint}/login`, loginRequest);
            if(response.data && response.data.token) {
                this.#storeToken(response.token);
                this.#storeUser(response.userResponse);
            }
            return response;
        } catch (error) {
            console.error("Login error:", error);
            throw error;
        }
    }

    async register(registerRequest) {
        try {
            
        } catch (error) {
            
        }
    }

    logout(){
        this.#removeToken();
        this.#removeUser();
    }


}
export default AuthService;