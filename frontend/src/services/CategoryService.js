import BaseService from "./BaseService";

class CategoryService extends BaseService {
    
    constructor(){
        super("/api/categories");
    }

    async findByAuthUser(params = {}) {
        try {
            const response = await this.api.get(`${this.endPoint}/me`, { params });
            return response;
        } catch (error) {
            console.error(error);
        }
    }
}

export default CategoryService; 