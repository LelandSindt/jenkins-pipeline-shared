static def boolean isSnapshot(version) {
  // if version is not numeric and version does not contain 'RELEASE' return true, else false
  return (!version.isNumber() && !version.contains('RELEASE'))
}

static def boolean isRelease(version) {
  // if version is numeric or contains 'RELEASE' then return true, else false
  return (version.isNumber() || version.contains('RELEASE'))
}

static def boolean isReleaseCandidate(branch) {
  // if branch contains 'RC' then return true, else false
  return branch.contains('RC')
}

// The following functions require Script Approval
// https://jenkins.io/doc/book/managing/script-approval/

static def boolean skipPipeline(script) {
  // if commitMessage contains 'skip pipeline' then true, else false
  return getLastCommitMessage(script).contains('skip pipeline')
}

static def boolean skipDeploy(script) {
  // if commitMessage contains 'skip deploy' then true, else false
  return getLastCommitMessage(script).contains('skip deploy')
}

static def boolean skipBuild(script) {
  // if commitMessage contains 'skip build' then true, else false
  return getLastCommitMessage(script).contains('skip build')
}

static def getLastCommitMessage(script) {
  def command = ""
  // if the build is taking place on the Manager
  if (!script.env.SSH_CONNECTION) {
    command = "git -C ${script.env.WORKSPACE} --no-pager log -1 --pretty=%B"
  } else { // the build is taking place on a Worker via SSH...
    command = "ssh -o StrictHostKeyChecking=no -p ${script.env.SSH_CONNECTION.split()[3]} ${script.env.SSH_CONNECTION.split()[2]} git -C ${script.env.WORKSPACE} --no-pager log -1 --pretty=%B"
  }
  def proc = command.execute()
  proc.waitFor()
  return proc.in.text
}
