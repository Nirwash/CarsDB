package Model

class Car {
    var iD = 0
    var name: String = ""
    var price: String = ""

    constructor() {}
    constructor(id: Int, name: String, price: String) {
        iD = id
        this.name = name
        this.price = price
    }

    constructor(name: String, price: String) {
        this.name = name
        this.price = price
    }
}

