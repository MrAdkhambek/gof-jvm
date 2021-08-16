package command

interface Command {
    fun apply()
    fun undo()
}

class CommandList(initialCommands: List<Command>) : List<Command> by initialCommands, Command {
    override fun apply() = forEach { it.apply() }
    override fun undo() = forEach { it.undo() }
}

class BankAccountCommand(
    private val bankAccount: BankAccount,
    private val action: Action,
    private val amount: Long
) : Command {

    enum class Action {
        DEPOSIT,
        WITHDRAW
    }

    override fun apply() = when (action) {
        Action.DEPOSIT -> bankAccount.deposit(amount)
        Action.WITHDRAW -> bankAccount.withDraft(amount)
    }

    override fun undo() = when (action) {
        Action.DEPOSIT -> bankAccount.withDraft(amount)
        Action.WITHDRAW -> bankAccount.deposit(amount)
    }
}

fun main() {
    val bankAccount = BankAccount()

//    val actions = arrayListOf<Command>(
//        BankAccountCommand(bankAccount, BankAccountCommand.Action.DEPOSIT, 100),
//        BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 200)
//    )
//
//    actions.forEach { it.apply() }
//    actions.forEach { it.undo() }

    val commandList = CommandList(
        arrayListOf<Command>(
            BankAccountCommand(bankAccount, BankAccountCommand.Action.DEPOSIT, 100),
            BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 200)
        )
    )

    commandList.apply()
    commandList.undo()
}