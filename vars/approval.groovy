def call() {
    def allowedApprovers = ["qa_hod"]
    def now = new Date()
    def dayOfWeek = now.format('EEEE')
    def timeOfDay = now.format('HH:mm:ss')
                     
    if (dayOfWeek != 'Saturday' && dayOfWeek != 'Sunday' && timeOfDay >= '08:45:00' && timeOfDay <= '20:32:00') {
        def approvers = input message: 'Deploy to production?',
                              ok: 'Approve',
                              submitterParameter:'APPROVER'
                              parameters: [
                                  [
                                      $class: 'StringParameterDefinition',
                                      name: 'APPROVER',
                                      defaultValue: allowedApprovers.join(','),
                                      description: 'Comma-separated list of allowed approvers'
                                  ]
                              ]
                                                
        def authorized = approvers in allowedApprovers
        if (!authorized) {
            error "User $approvers is not authorized to approve the deployment"
        }
    }
}
