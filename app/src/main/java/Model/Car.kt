package Model

class Car {
    var id = 0
    var name: String = ""
    var price: String = ""

    constructor() {}
    constructor(id: Int, name: String, price: String) {
        this.id = id
        this.name = name
        this.price = price
    }

    constructor(name: String, price: String) {
        this.name = name
        this.price = price
    }
}

