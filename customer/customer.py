import aiohttp
from quart import Quart, request, jsonify
from pydantic import BaseModel

app = Quart(__name__)

class CustomerBody(BaseModel):
    username: str
    userID: str

class ExternalAPIFetcher:
    def __init__(self):
        self.address_url = "https://jsonplaceholder.typicode.com/posts/1"
        self.phone_url = "https://jsonplaceholder.typicode.com/posts/2"

    async def _fetch_data_from_api(self, url: str, body: dict):
        async with aiohttp.ClientSession() as session:
            try:
                async with session.post(url, json=body) as response:
                    response.raise_for_status()
                    return await response.json()
            except Exception as e:
                return f"Fail: {e}"

    async def get_combined_data(self, data: CustomerBody) -> bool:
        phone_body = {"username": data.username, "userID": data.userID}
        address_body = {"username": data.username, "userID": data.userID}

        phone_response = await self._fetch_data_from_api(self.phone_url, phone_body)
        address_response = await self._fetch_data_from_api(self.address_url, address_body)
        print(phone_response)

        if "Fail" not in phone_response and "Fail" not in address_response:
            return True
        return False

class Customer:
    def __init__(self):
        self.app = app
        self.fetcher = ExternalAPIFetcher()
        self._setup_routes()

    def _setup_routes(self):
        @self.app.route('/trigger', methods=['POST'])
        async def trigger():
            try:
                body = CustomerBody.model_validate_json(await request.data)
            except Exception as e:
                return jsonify({"error": "Please provide a valid body"}), 400

            data = await self.fetcher.get_combined_data(body)
            if data:
                return "All ok", 200
            else:
                return "Nope", 500

    def run(self):
        self.app.run(host="0.0.0.0", port=5000)

if __name__ == "__main__":
    api = Customer()
    api.run()
