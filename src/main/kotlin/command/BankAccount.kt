package command

class BankAccount {

    var balance: Long = 0L
        private set

    var overDraftLimit: Long = -500L
        private set

    fun deposit(amount: Long) {
        balance += amount
        println("Current amount = $balance")
    }

    fun withDraft(amount: Long) {
        if (balance - amount >= overDraftLimit) {
            balance -= amount
            println("Current amount = $balance")
        }
    }
}