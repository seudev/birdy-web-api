alias reload-bashrc='source $WORKDIR/.devcontainer/.bashrc'
alias reload-env='source $WORKDIR/.env'
alias workdir='cd $WORKDIR'
alias start='mvn quarkus:dev'
alias test='mvn test'
alias package='mvn clean package'
alias compile='mvn clean compile'
alias clean='mvn clean'
alias format='mvn formatter:format'
alias validate-format='mvn formatter:validate'
alias build-tree='mvn quarkus-bootstrap:build-tree'