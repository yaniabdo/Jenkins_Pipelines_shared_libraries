def call(String environment, String action) {
    if (action.toLowerCase() == "start") {
        sh """
            aws ec2 describe-instances \
                --filters "Name=tag:Environment,Values=${environment}" "Name=instance-state-name,Values=stopped" \
                --query 'Reservations[].Instances[].InstanceId' \
                --output text | xargs -I {} aws ec2 start-instances --instance-ids {}
        """
    } 
    else if (action.toLowerCase() == "stop") {
        sh """
            aws ec2 describe-instances \
                --filters "Name=tag:Environment,Values=${environment}" "Name=instance-state-name,Values=running" \
                --query 'Reservations[].Instances[].InstanceId' \
                --output text | xargs -I {} aws ec2 stop-instances --instance-ids {}
        """
    }
    else {
        error "Invalid action: ${action}. Must be either 'start' or 'stop'"
    }
}
