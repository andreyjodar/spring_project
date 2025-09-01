import api from "../config/AxiosConfig";

class BaseService {

    constructor(endPoint) {
        this.endPoint = endPoint;
        this.api = api;
    }

    async insert(data){
        const response = await this.api.post(this.endPoint, data);
        return response;
    }

    async update(id, data) {
        const response = await this.api.put(`${this.endPoint}/${id}`, data);
        return response;
    }

    async delete(id){
        const response = await this.api.delete(`${this.endPoint}/${id}`);
        return response;
    }

    async findById(id){
        const response = await this.api.get(`${this.endPoint}/${id}`);
        return response;
    }

    async findAll(params = {}){
        try {
            const response = await this.api.get(this.endPoint, { params });
            return response;
        } catch (error) {
            console.log(error);
        }
    }
}

export default BaseService;