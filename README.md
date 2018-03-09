# jenkins-pipeline-shared
Shared Jenkins Pipeline Tools

# Dynamic Retrival
To Dynamically retrieve this shared library add the following to the top of your Jenkinsfile 
```
library identifier: 'toolslib@master', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'https://github.com/GrowMon/jenkins-pipeline-shared.git'])
```

## Usage in Declaritive Jenkinsfile 

```
environment{
  skipPipeline = check.skipPipeline(this)
}
```
-or-
```
when {
  not { expression { check.skipPipeline(this) } }
}
```


## In-Process Script Approval
To Pre-Approve elements that require Script Approval add the following elements to /var/jenkins_home/scriptApproval.xml
```
<?xml version='1.0' encoding='UTF-8'?>
<scriptApproval plugin="script-security@1.40">
  <approvedScriptHashes/>
  <approvedSignatures>
    <string>method java.lang.Process waitFor</string>
    <string>staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods execute java.lang.String</string>
    <string>staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getText java.io.InputStream</string>
    <string>staticMethod org.codehaus.groovy.runtime.ProcessGroovyMethods getIn java.lang.Process</string>
  </approvedSignatures>
  <aclApprovedSignatures/>
  <approvedClasspathEntries/>
  <pendingScripts/>
  <pendingSignatures/>
  <pendingClasspathEntries/>
</scriptApproval>
```
 
Note: ```staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods execute java.lang.String``` can introduced a security vulnerability. 
