// vars/shutdownEC2ByTag.groovy
def call(String environment) {
    sh """
        aws ec2 describe-instances \
            --filters "Name=tag:Environment,Values=${environment}" "Name=instance-state-name,Values=running" \
            --query 'Reservations[].Instances[].InstanceId' \
            --output text | xargs -I {} aws ec2 stop-instances --instance-ids {}
    """
}
