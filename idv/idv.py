from random import choice

class PhoneNumberProvider:
    def _make_name_lookup(self) -> str:
        return choice(["123456789", "987654321", "564738291", "78596749682"])
    
    def provide_phone_number(self, name: str) -> str:
        return self._make_name_lookup()
        

